package eu.borderprinces.entities;

import eu.borderprinces.entities.unit.Player;
import lombok.Data;
import lombok.NonNull;

import java.util.Optional;

@Data
public abstract class Unit {

    private long teamId;
    private int maxHealth;
    private int health;

    private Tile tile;
    private final String icon;

    public Unit(long teamId, @NonNull Tile tile, @NonNull String icon, int maxHealth) {
        this.teamId = teamId;
        this.health = maxHealth;
        this.maxHealth = maxHealth;
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

    public void move(Tile tile) {
        if(tile != null && tile != this.tile) {
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
            this.health = 0;
            this.tile.setUnit(null);
            this.tile = null;
        }
    }

    public abstract void takeAction();

    @Override
    public String toString() {
        return "Unit{" +
                "health=" + health +
                ", icon='" + icon + '\'' +
                '}';
    }
}
