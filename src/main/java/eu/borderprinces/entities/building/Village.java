package eu.borderprinces.entities.building;

import eu.borderprinces.entities.Color;
import eu.borderprinces.entities.Game;
import eu.borderprinces.entities.Tile;
import lombok.Getter;
import lombok.NonNull;

public class Village extends Recruiter {

    @Getter
    private int grainFields;

    public Village(long teamId, @NonNull Tile tile, @NonNull String icon) {
        super(teamId, tile, icon, Color.PURPLE);
        this.grainFields = 1;
    }

    @Override
    public void takeTurn(Game game) {
        this.getTile().getUnits().stream()
                .filter(unit -> this.getTeamId().equals(unit.getTeamId()))
                .forEach(unit -> unit.setHealth(unit.getMaxHealth()));
    }

    @Override
    public void destroy(Game game) {

    }

    public void addGrainField() {
        this.grainFields++;
    }

    public void removeGrainField() {
        this.grainFields++;
    }
}
