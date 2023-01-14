package eu.borderprinces.entities.unit;

import eu.borderprinces.entities.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Comparator;
import java.util.Random;

import static eu.borderprinces.BorderPrincesConstants.MONSTER_LAIR;
import static eu.borderprinces.entities.unit.UnitLogic.DEFEND;

@Getter
@Setter
public class Soldier extends Unit {

    Game game;
    UnitLogic unitLogic;
    Target protectionTarget;
    Long patrolRange = 5L;

    public Soldier(long teamid, @NonNull Tile tile, @NonNull String icon, @NonNull Game game, UnitLogic unitLogic) {
        super(teamid, tile, icon, 1);
        this.game = game;
        this.unitLogic = unitLogic;
        this.protectionTarget = tile.getBuilding();
        game.units.add(this);
    }

    public void setUnitLogic(UnitLogic unitLogic) {
        this.unitLogic = unitLogic;
        this.currentTarget = null;
        if(DEFEND.equals(unitLogic)){
            this.currentTarget = protectionTarget;
        }
    }

    @Override
    public void takeAction() {
        // dead units shouldn't move
        // units can be killed in another units action
        if (getHealth() > 0) {
            switch (this.unitLogic) {
                case DEFEND -> this.defend();
                case PATROL -> this.patrol();
                case SEARCH_AND_DESTROY -> this.killAndDestroy();
            }
        }
    }

    private void defend() {
        if(!this.getTile().equals(protectionTarget.getTile())){
            moveRandomToTarget();
        } else {
            this.currentTarget = protectionTarget;
        }
    }

    private void killAndDestroy() {
        if (this.getTile().getBuilding() != null
                && MONSTER_LAIR.equals(this.getTile().getBuilding().getIcon())) {
            this.getTile().destroyBuilding(this.game);
        } else {
            if (currentTarget == null) {
                this.setCurrentTarget();
            }
            this.moveRandomToTarget();
        }
    }

    private void patrol() {
        Tile defendTile = this.protectionTarget.getTile();
        this.currentTarget = this.protectionTarget;
        game.units.stream()
                .filter(otherUnit -> otherUnit.getTeamId() != this.getTeamId())
                .map(Unit::getTile)
                .filter(tile -> tile.getDistance(tile, defendTile) < patrolRange)
                .findFirst()
                .ifPresent(tile -> this.currentTarget = tile.getUnits().get(0));
        moveRandomToTarget();
    }

    private void moveRandomToTarget() {
        Random rand = new Random();
        int direction = rand.nextInt(0, 9);
        if (direction > 1) {
            int rd = currentTarget.getTile().getRow() - this.getTile().getRow();
            int cd = currentTarget.getTile().getColumn() - this.getTile().getColumn();
            int r2 = rd * rd;
            int c2 = cd * cd;
            if (r2 > c2) {
                this.move(
                        this.game,
                        rd > 0 ? 1 : -1,
                        0
                );
            } else {
                this.move(
                        this.game,
                        0,
                        cd > 0 ? 1 : -1
                );
            }
        } else if (direction == 1) {
            this.move(
                    this.game,
                    0,
                    rand.nextInt(-1, 2)
            );
        } else if (direction == 0) {
            this.move(
                    this.game,
                    rand.nextInt(-1, 2),
                    0
            );
        }
    }

    private void setCurrentTarget() {
        currentTarget = game.buildings.stream()
                .filter(b -> this.getTeamId() != b.getTeamId())
                .map(Building::getTile)
                .min(Comparator.comparingInt(x -> x.getDistance(x, getTile())))
                .map(Tile::getBuilding)
                .orElseThrow();
    }
}
