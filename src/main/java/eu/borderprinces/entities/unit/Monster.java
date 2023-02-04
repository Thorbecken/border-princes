package eu.borderprinces.entities.unit;

import eu.borderprinces.entities.Color;
import eu.borderprinces.entities.Game;
import eu.borderprinces.entities.Tile;
import eu.borderprinces.entities.Unit;
import lombok.NonNull;

import static eu.borderprinces.BorderPrincesConstants.*;

public class Monster extends Unit {

    public Monster(long teamid, @NonNull Tile tile, @NonNull String icon, @NonNull Game game, @NonNull UnitLogic unitLogic) {
        super(teamid, tile, icon, 1, game, unitLogic, Color.RED);
    }

    @Override
    public void killAndDestroy() {
        // dead units shouldn't move
        // units can be killed in another units action
        if (getHealth() > 0) {
            if (this.getTile().getBuilding() != null
                    && this.getTeamId() != this.getTile().getBuilding().getTeamId()) {
                this.getTile().destroyBuilding(this.getGame());
            } else if (this.getTile().getBuilding() == null
                    && BARE_GROUND.equals(this.getTile().getTerrain().getIcon())) {
                this.getTile().createLair(MONSTER_LAIR, this.getTeamId());
            } else {
                if (currentTarget == null) {
                    this.setCurrentTarget();
                }
                this.moveRandomToTarget(false);
            }
        }
    }

    @Override
    public UnitType getUnitType() {
        return UnitType.MONSTER;
    }
}
