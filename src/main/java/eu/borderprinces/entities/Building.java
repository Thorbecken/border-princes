package eu.borderprinces.entities;

import lombok.Data;
import lombok.NonNull;

@Data
public abstract class Building implements Target {

    private Long teamId;
    private final Tile tile;

    private final String icon;
    private final Color color;

    public Building(long teamId, @NonNull Tile tile, @NonNull String icon, @NonNull Color color) {
        this.teamId = teamId;
        this.tile = tile;
        this.icon = icon;
        this.color = color;
    }

    public abstract void takeTurn(Game game);

    @Override
    public String toString() {
        return "Building{" +
                "icon='" + icon + '\'' +
                '}';
    }

    public String getColored() {
        return this.color.getCode() + this.icon;
    }
}
