package eu.borderprinces.entities.building;

import eu.borderprinces.entities.Building;
import eu.borderprinces.entities.Game;
import eu.borderprinces.entities.Tile;
import lombok.NonNull;

import java.util.Optional;

public class Village extends Building {

    public Village(long teamId, @NonNull Tile tile, @NonNull String building) {
        super(teamId, tile, building);
    }

    @Override
    public void takeTurn(Game game) {
        Optional.ofNullable(this.getTile().getUnit())
                .filter(unit -> this.getTeamId().equals(unit.getTeamId()))
                .ifPresent(unit -> unit.setHealth(unit.getMaxHealth()));
    }

}
