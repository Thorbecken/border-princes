package eu.borderprinces.entities.building;

import eu.borderprinces.entities.Building;
import eu.borderprinces.entities.Color;
import eu.borderprinces.entities.Game;
import eu.borderprinces.entities.Tile;
import lombok.NonNull;

public class Grainfield extends Building {
    private final Capitol capitol;

    public Grainfield(long teamId, @NonNull Tile tile, @NonNull String icon, @NonNull Capitol capitol) {
        super(teamId, tile, icon, Color.PURPLE);
        this.capitol = capitol;
        this.capitol.addGrainField();
    }

    @Override
    public void takeTurn(Game game) {

    }

    @Override
    public void destroy(Game game) {
        this.capitol.removeGrainField();
        game.buildings.remove(this);
    }
}
