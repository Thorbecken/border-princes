package eu.borderprinces.entities.unit;

import eu.borderprinces.entities.Color;
import eu.borderprinces.entities.Game;
import eu.borderprinces.entities.Tile;
import eu.borderprinces.entities.Unit;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class Builder extends Unit {

    public Builder(long teamid, @NonNull Tile tile, @NonNull String icon, @NonNull Game game, UnitLogic unitLogic) {
        super(teamid, tile, icon, 1, game, unitLogic, Color.PURPLE);
    }

    @Override
    public UnitType getUnitType() {
        return UnitType.BUILDER;
    }
}
