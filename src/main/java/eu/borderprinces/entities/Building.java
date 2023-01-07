package eu.borderprinces.entities;

import lombok.Data;
import lombok.NonNull;

@Data
public abstract class Building {

    private Long teamId;
    private final Tile tile;

    private final String icon;

    public Building(long teamId, @NonNull Tile tile, @NonNull String icon) {
        this.teamId = teamId;
        this.tile = tile;
        this.icon = icon;
    }

    public abstract void takeTurn(Game game);

    @Override
    public String toString() {
        return "Building{" +
                "icon='" + icon + '\'' +
                '}';
    }
}
