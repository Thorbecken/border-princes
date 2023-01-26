package eu.borderprinces.entities;

import eu.borderprinces.BorderPrincesConstants;
import eu.borderprinces.entities.pathfinding.Pathfinder;
import eu.borderprinces.entities.unit.Player;
import eu.borderprinces.entities.unit.UnitLogic;
import lombok.Data;
import lombok.NonNull;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static eu.borderprinces.BorderPrincesConstants.MONSTER_LAIR;
import static eu.borderprinces.entities.unit.UnitLogic.DEFEND;

@Data
public abstract class Unit implements Target {

    static long nextId = 1L;
    private long id;
    private long teamId;
    private int maxHealth;
    private int health;
    private int actionPoints;
    private Tile tile;
    protected Target currentTarget;
    private final String icon;
    private final Color color;
    private final Game game;
    private UnitLogic unitLogic;
    private Target protectionTarget;
    private Long patrolRange = 5L;
    private List<Tile> currentPath;

    public Unit(long teamId, @NonNull Tile tile, @NonNull String icon, int maxHealth, @NonNull Game game, UnitLogic unitLogic, Color color) {
        this.id = nextId++;
        this.teamId = teamId;
        this.health = maxHealth;
        this.maxHealth = maxHealth;
        this.tile = tile;
        this.icon = icon;
        this.game = game;
        this.unitLogic = unitLogic;
        this.protectionTarget = tile.getBuilding();
        this.currentPath = null;
        this.color = color;
        this.actionPoints = 1;
        tile.addUnit(this);
        game.units.add(this);
    }

    public void setUnitLogic(UnitLogic unitLogic) {
        this.unitLogic = unitLogic;
        this.currentTarget = null;
        if (DEFEND.equals(unitLogic)) {
            this.currentTarget = protectionTarget;
        }
    }

    public void move(Game game, int row, int colum) {
        Optional.of(game)
                .map(g -> g.getTile(this.getTile().getRow() + row,
                        this.getTile().getColumn() + colum))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .ifPresent(this::move);
    }

    protected void move(Tile tile) {
        if (tile != null
                && tile != this.tile
                && !BorderPrincesConstants.WATER.equals(tile.getTerrain().getIcon())) {
            if (tile.openOrFriendly(this.teamId)) {
                this.tile.removeUnit(this);
                tile.addUnit(this);
                this.tile = tile;
                this.actionPoints -= tile.getTerrain().getTerrainType().getMovementCost();
                this.actionPoints++;
            } else {
                tile.getUnits().get(0).combat(this);
            }
        }
    }

    private void combat(Unit unit) {
        unit.health = unit.health - 1;
        if (unit.health == 0) {
            unit.die();
        }
        this.health = this.health - 1;
        if (this.health == 0) {
            this.die();
        }
    }

    protected void die() {
        if (this instanceof Player) {
            throw new RuntimeException("You lose '_' ");
        } else {
            this.health = 0;
            this.tile.removeUnit(this);
            this.tile = null;
        }
    }

    public void takeAction() {
        actionPoints--;
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

    protected void defend() {
        if (!this.getTile().equals(protectionTarget.getTile())) {
            moveRandomToTarget();
        } else {
            this.currentTarget = protectionTarget;
        }
    }

    protected void killAndDestroy() {
        if (this.getTile().getBuilding() != null
                && MONSTER_LAIR.equals(this.getTile().getBuilding().getIcon())) {
            this.getTile().destroyBuilding(this.game);
            this.currentTarget = null;
        } else {
            if (currentTarget == null) {
                this.setCurrentTarget();
            }
            this.moveRandomToTarget();
        }
    }

    protected void patrol() {
        Tile defendTile = this.protectionTarget.getTile();
        this.currentTarget = this.protectionTarget;
        game.units.stream()
                .filter(otherUnit -> otherUnit.getTeamId() != this.getTeamId())
                .filter(Unit::notDead)
                .map(Unit::getTile)
                .filter(tile -> defendTile.getDistance(tile) < patrolRange)
                .findFirst()
                .ifPresent(tile -> this.currentTarget = tile.getUnits().get(0));
        calculatePath();

        moveRandomToTarget();
    }

    private boolean notDead() {
        return this.health > 0;
    }

    protected void moveRandomToTarget() {
        Random rand = new Random();
        int direction = rand.nextInt(0, 9);
        if (direction > 1 && !currentPath.isEmpty()) {
            Tile nextTile = currentPath.remove(0);
            this.move(nextTile);
        } else if (direction == 1) {
            this.move(
                    this.game,
                    0,
                    rand.nextInt(-1, 2)
            );
            this.calculatePath();
        } else if (direction == 0) {
            this.move(
                    this.game,
                    rand.nextInt(-1, 2),
                    0
            );
            this.calculatePath();
        }
    }

    protected void setCurrentTarget() {
        currentTarget = game.buildings.stream()
                .filter(b -> this.getTeamId() != b.getTeamId())
                .map(Building::getTile)
                .min(Comparator.comparingInt(x -> x.getDistance(this.getTile())))
                .map(Tile::getBuilding)
                .orElseThrow();
        this.calculatePath();
    }

    private void calculatePath() {
        this.currentPath = new Pathfinder(game).pathfind(this.tile, currentTarget.getTile());
    }

    @Override
    public String toString() {
        return "Unit{" +
                "health=" + health +
                ", icon='" + icon + '\'' +
                '}';
    }

    public void addActionPoint() {
        this.actionPoints++;
    }

    public void removeActionPoint() {
        this.actionPoints--;
    }

    public String getColored() {
        return this.color.getCode() + this.icon;
    }
}
