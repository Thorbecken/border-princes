package eu.borderprinces.entities.unit;

import eu.borderprinces.entities.Building;
import eu.borderprinces.entities.Game;
import eu.borderprinces.entities.Tile;
import eu.borderprinces.entities.Unit;
import lombok.NonNull;

import java.util.Comparator;
import java.util.Random;

import static eu.borderprinces.BorderPrincesConstants.TEAM_PLAYER;
import static eu.borderprinces.BorderPrincesConstants.VILLAGE;

public class Monster extends Unit {

    Game game;

    public Monster(long teamid, @NonNull Tile tile, @NonNull String icon, @NonNull Game game) {
        super(teamid, tile, icon, 1);
        this.game = game;
        game.units.add(this);
    }

    @Override
    public void takeAction() {
        // dead units shouldn't move
        // units can be killed in another units action
        if(this.getHealth() > 0) {
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
        }

        // dead units shouldn't move
        // units can die during movement
        if(getHealth() > 0) {
            if (this.getTile().getBuilding() != null
                    && VILLAGE.equals(this.getTile().getBuilding().getIcon())) {
                this.getTile().destroyBuilding(this.game);
            }
        }
    }

    private Tile getNearestVillage(Tile tile, Game game) {
        return game.buildings.stream()
                .filter(b -> TEAM_PLAYER.equals(b.getTeamId()))
                .map(Building::getTile)
                .min(Comparator.comparingInt(x -> x.getDistance(x, tile)))
                .orElseThrow();
    }
}
