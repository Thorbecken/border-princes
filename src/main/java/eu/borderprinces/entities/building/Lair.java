package eu.borderprinces.entities.building;

import eu.borderprinces.entities.Building;
import eu.borderprinces.entities.Game;
import eu.borderprinces.entities.Tile;
import eu.borderprinces.entities.Unit;
import eu.borderprinces.entities.unit.Monster;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static eu.borderprinces.entities.unit.UnitLogic.*;


public class Lair extends Building {

    public final String monsterType;
    private final List<Unit> spawnedUnits = new ArrayList<>();

    public Lair(long teamId, @NonNull Tile tile, @NonNull String building, @NonNull String monsterType) {
        super(teamId, tile, building);
        this.monsterType = monsterType;
    }

    @Override
    public void takeTurn(Game game) {
        this.purgeTheDead();
        Random random = new Random();
        int change = random.nextInt(10);
        if (change == 0) {
            if (!this.hasDefender()) {
                this.spawnedUnits.add(new Monster(this.getTeamId(), this.getTile(), this.monsterType, game, DEFEND));
            } else if (!this.hasPatroller()) {
                this.spawnedUnits.add(new Monster(this.getTeamId(), this.getTile(), this.monsterType, game, PATROL));
            } else {
                this.spawnedUnits.add(new Monster(this.getTeamId(), this.getTile(), this.monsterType, game, SEARCH_AND_DESTROY));
            }
        }
    }

    public void purgeTheDead() {
        List<Unit> deadUnits = spawnedUnits.stream()
                .filter(unit -> unit.getHealth() < 1)
                .toList();
        deadUnits.forEach(spawnedUnits::remove);
    }

    public boolean hasDefender() {
        return this.spawnedUnits.stream()
                .anyMatch(unit -> DEFEND.equals(unit.getUnitLogic()));
    }

    public boolean hasPatroller() {
        return this.spawnedUnits.stream()
                .anyMatch(unit -> PATROL.equals(unit.getUnitLogic()));
    }
}
