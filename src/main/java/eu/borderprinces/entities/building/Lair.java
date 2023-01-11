package eu.borderprinces.entities.building;

import eu.borderprinces.entities.Building;
import eu.borderprinces.entities.Game;
import eu.borderprinces.entities.unit.Monster;
import eu.borderprinces.entities.Tile;
import lombok.NonNull;

import java.util.Random;


public class Lair extends Building {

    public final String monsterType;

    public Lair(long teamId, @NonNull Tile tile, @NonNull String building, @NonNull String monsterType) {
        super(teamId, tile, building);
        this.monsterType = monsterType;
    }

    @Override
    public void takeTurn(Game game) {
        if(this.getTile().noUnits()) {
            Random random = new Random();
            int change = random.nextInt(10);
            if (change == 0) {
                new Monster(this.getTeamId(), this.getTile(), this.monsterType, game);
            }
        }
    }
}
