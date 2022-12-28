package eu.borderprinces.entities;

import eu.borderprinces.ConsoleActions;
import lombok.Data;
import lombok.NonNull;

import java.util.EnumSet;
import java.util.Optional;

import static eu.borderprinces.BorderPrincesConstants.MONSTER;
import static eu.borderprinces.BorderPrincesConstants.PLAYER;

@Data
public class Unit {

    private int health;

    private Tile tile;
    private final String icon;

    public Unit(@NonNull Tile tile, @NonNull String icon) {
        if(MONSTER.equals(icon)){
            this.health = 1;
        } else if (PLAYER.equals(icon)){
            this.health = 10;
        } else {
            throw new RuntimeException();
        }
        this.tile = tile;
        this.icon = icon;
        tile.setUnit(this);
    }

    public void move(Game game, int row, int colum) {
        Optional.of(game)
                .map(g -> g.getTile(this.getTile().getRow()+row,
                        this.getTile().getColumn() + colum))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .ifPresent(this::move);
    }

    public void move(@NonNull Tile tile) {
        if(tile != this.tile) {
            if (tile.getUnit() == null) {
                this.tile.setUnit(null);
                tile.setUnit(this);
                this.tile = tile;
            } else {
                tile.getUnit().combat(this);
            }
        }
    }

    private void combat(Unit unit) {
        unit.health = unit.health-1;
        if(unit.health == 0){
            unit.die();
        }
        this.health = this.health-1;
        if(this.health == 0){
            this.die();
        }
    }

    protected void die() {
        if (this instanceof Player){
            throw new RuntimeException("You lose '_' ");
        } else {
            this.tile.setUnit(null);
            this.tile = null;
        }
    }

    public EnumSet<ConsoleActions> getActions() {
        return EnumSet.of(ConsoleActions.QUIT,
                ConsoleActions.NEW_GAME,
                ConsoleActions.UP,
                ConsoleActions.DOWN,
                ConsoleActions.RIGHT,
                ConsoleActions.LEFT,
                ConsoleActions.CLEAR_LAIR);
    }

    @Override
    public String toString() {
        return "Unit{" +
                "health=" + health +
                ", icon='" + icon + '\'' +
                '}';
    }
}
