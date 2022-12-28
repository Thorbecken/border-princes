package eu.borderprinces.entities;

import lombok.NonNull;

import java.util.Comparator;
import java.util.Random;

import static eu.borderprinces.BorderPrincesConstants.VILLAGE;

public class Monster extends Unit {

    Game game;

    public Monster(@NonNull Tile tile, @NonNull String icon, @NonNull Game game) {
        super(tile, icon);
        this.game = game;
        game.monsters.add(this);
    }

    public void move() {
        Random rand = new Random();
        int direction = rand.nextInt(0, 9);
        if (direction > 1) {
            Tile targetTile = getNearestVillage(this.getTile(), game);
            int rd = targetTile.getRow() - this.getTile().getRow();
            int cd = targetTile.getColumn() - this.getTile().getColumn();
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
        if(this.getTile().getBuilding() != null
                && VILLAGE.equals(this.getTile().getBuilding().getIcon())){
            this.getTile().destroyBuilding(this.game);
        }
    }

    private Tile getNearestVillage(Tile tile, Game game) {
        return game.playerBuildings.stream()
                .map(Building::getTile)
                .min(Comparator.comparingInt(x -> x.getDistance(x, tile)))
                .orElseThrow();
    }

    protected void die() {
        super.die();
    }
}
