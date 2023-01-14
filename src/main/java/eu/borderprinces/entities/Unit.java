package eu.borderprinces.entities;

import eu.borderprinces.entities.unit.Player;
import lombok.Data;
import lombok.NonNull;

import java.util.Optional;

@Data
public abstract class Unit implements Target {

    static long nextId = 1L;

    private long id;
    private long teamId;
    private int maxHealth;
    private int health;
    private Tile tile;
    protected Target currentTarget;
    private final String icon;

    public Unit(long teamId, @NonNull Tile tile, @NonNull String icon, int maxHealth) {
        this.id = nextId++;
        this.teamId = teamId;
        this.health = maxHealth;
        this.maxHealth = maxHealth;
        this.tile = tile;
        this.icon = icon;
        tile.addUnit(this);
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
            if (tile.openOrFriendly(this.teamId)) {
                this.tile.removeUnit(this);
                tile.addUnit(this);
                this.tile = tile;
            } else {
                tile.getUnits().get(0).combat(this);
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
            this.tile.removeUnit(this);
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
