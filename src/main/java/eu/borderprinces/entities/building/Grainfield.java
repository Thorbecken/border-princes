package eu.borderprinces.entities.building;

import eu.borderprinces.entities.Building;
import eu.borderprinces.entities.Color;
import eu.borderprinces.entities.Game;
import eu.borderprinces.entities.Tile;
import lombok.NonNull;

public class Grainfield extends Building {

    private final Village village;

    public Grainfield(long teamId, @NonNull Tile tile, @NonNull String icon, @NonNull Village village) {
        super(teamId, tile, icon, Color.PURPLE);
        this.village = village;
        this.village.grainFields++;
    }

    @Override
    public void takeTurn(Game game) {

    }

    @Override
    public void destroy(Game game) {
        this.village.grainFields--;
        game.buildings.remove(this);
    }
}
