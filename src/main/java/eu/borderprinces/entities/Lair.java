package eu.borderprinces.entities;

import lombok.NonNull;

import java.util.Random;

import static eu.borderprinces.BorderPrincesConstants.MONSTER;


public class Lair extends Building {

    public Lair(@NonNull Tile tile, @NonNull String building) {
        super(tile, building);
    }

    public void takeTurn(Game game) {
        Random random = new Random();
        int change = random.nextInt(10);
        if (change == 0) {
            this.getTile().setUnit(new Monster(this.getTile(), MONSTER, game));
        }
    }
}
