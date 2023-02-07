package eu.borderprinces.entities.building;

import eu.borderprinces.entities.Game;
import eu.borderprinces.entities.Tile;
import eu.borderprinces.entities.unit.UnitType;
import lombok.NonNull;

import static eu.borderprinces.entities.unit.UnitLogic.*;


public class Capitol extends Village {

    public Capitol(long teamId, @NonNull Tile tile, @NonNull String icon) {
        super(teamId, tile, icon);
        this.addOrder(new RecruitmentOrder(1, 1, UnitType.FARMER, FARM));
        this.addOrder(new RecruitmentOrder(2, 3, UnitType.SOLDIER, SEARCH_AND_DESTROY));
        this.addOrder(new RecruitmentOrder(3, 1, UnitType.BUILDER, BUILD_VILLAGE));
        this.addOrder(new RecruitmentOrder(4, 99, UnitType.SOLDIER, SEARCH_AND_DESTROY));
    }

    @Override
    public void takeTurn(Game game) {
        this.getTile().getUnits().stream()
                .filter(unit -> this.getTeamId().equals(unit.getTeamId()))
                .forEach(unit -> unit.setHealth(unit.getMaxHealth()));

        if (this.getCurrentNumberOfUnits() < this.getGrainFields()) {
            recruit(game);
        }
    }
}
