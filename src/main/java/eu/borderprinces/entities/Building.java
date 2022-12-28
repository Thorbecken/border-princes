package eu.borderprinces.entities;

import lombok.Data;
import lombok.NonNull;

import java.util.Random;

@Data
public class Building {

    private final Tile tile;

    private final String icon;

    public Building(@NonNull Tile tile, @NonNull String icon) {
        this.tile = tile;
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "Building{" +
                "icon='" + icon + '\'' +
                '}';
    }

    public void takeTurn() {
        Random random = new Random();
        int change = random.nextInt(10);
        if (change == 0) {

        }
    }
}
