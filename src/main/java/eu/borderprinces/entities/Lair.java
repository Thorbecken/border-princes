package eu.borderprinces.entities;

import lombok.NonNull;

import java.util.Random;

import static eu.borderprinces.BorderPrincesConstants.MONSTER;
import static eu.borderprinces.BorderPrincesConstants.TEAM_MONSTERS;


public class Lair extends Building {

    public Lair(long teamId, @NonNull Tile tile, @NonNull String building) {
        super(teamId, tile, building);
    }

    public void takeTurn(Game game) {
        Random random = new Random();
        int change = random.nextInt(10);
        if (change == 0) {
            this.getTile().setUnit(new Monster(TEAM_MONSTERS, this.getTile(), MONSTER, game));
        }
    }
}
