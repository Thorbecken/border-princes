package eu.borderprinces.entities;

import eu.borderprinces.entities.building.Grainfield;
import eu.borderprinces.entities.building.Lair;
import eu.borderprinces.entities.building.Village;
import eu.borderprinces.entities.pathfinding.GraphNode;
import lombok.Getter;
import lombok.NonNull;

import java.util.*;

import static eu.borderprinces.BorderPrincesConstants.*;

public class Tile implements GraphNode {
    static long nextId = 1L;

    @Getter
    private final Long id;
    @Getter
    @NonNull
    private final Integer row;
    @Getter
    @NonNull
    private final Integer column;

    public boolean openOrFriendly(Long teamId) {
        return this.controllingTeam == null || teamId.equals(this.controllingTeam);
    }

    private Long controllingTeam;
    private String currentIcon;
    private Terrain terrain;
    private Building building;
    private final List<Unit> units;

    public Tile(String terrain, int row, int column) {
        this.id = nextId++;
        this.row = row;
        this.column = column;
        this.units = new ArrayList<>();
        this.controllingTeam = null;

        setTerrain(terrain);
    }

    private void setCurrentIcon() {
        if(!this.units.isEmpty()) {
            this.currentIcon = units.get(0).getColored();
        } else if (this.building != null){
            this.currentIcon = this.building.getColored();
        } else {
            this.currentIcon = terrain.getTerrainType().getColored();
        }
    }

    private void setTerrain(String terrain) {
        this.terrain = new Terrain(row, column, terrain, TerrainType.getTerrainType(terrain));
        setCurrentIcon();
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public void createLair(long teamId) {
        this.building = new Lair(teamId, this, MONSTER_LAIR, MONSTER);
        setCurrentIcon();
    }

    public void createVillage() {
        this.building = new Village(TEAM_PLAYER, this, VILLAGE);
        setCurrentIcon();
    }

    public void createGrainField(Game game) {
        Village nearestFriendlyVillage = game.buildings.stream()
                .filter(building -> building instanceof Village)
                .map(building -> ((Village) building))
                .min(Comparator.comparing(village -> village.getTile().getDistance(game.prince.getTile())))
                .orElseThrow();
        this.building = new Grainfield(TEAM_PLAYER, this, GRAIN_FIELD, nearestFriendlyVillage);
        setCurrentIcon();
        game.buildings.add(this.getBuilding());
    }

    public void addUnit(Unit unit) {
        if (this.units.isEmpty()) {
            this.controllingTeam = unit.getTeamId();
        } else if (!this.controllingTeam.equals(unit.getTeamId())) {
            throw new RuntimeException("Tile is owned by another team");
        }

        this.units.add(unit);
        setCurrentIcon();
    }

    public void removeUnit(Unit unit) {
        this.units.remove(unit);
        if (units.isEmpty()) {
            this.controllingTeam = null;
        }
        setCurrentIcon();
    }

    public Building getBuilding() {
        return building;
    }

    public void destroyBuilding(Game game) {
        this.building.destroy(game);
        this.building = null;
        setCurrentIcon();
    }

    public String getIcon() {
        return currentIcon;
    }

    public int getDistance(Tile targetTile) {
        int rd = Math.abs(targetTile.getRow() - this.getRow());
        int cd = Math.abs(targetTile.getColumn() - this.getColumn());
        return rd + cd;
    }

    @Override
    public String toString() {
        return "Tile{" +
                "row=" + row +
                ", column=" + column +
                ", currentIcon='" + currentIcon + '\'' +
                ", terrain=" + terrain +
                ", building=" + building +
                ", units=" + units +
                '}';
    }

    public List<Unit> getUnits() {
        return new ArrayList<>(units);
    }

    public Set<Long> getLandNeighbours(Game game) {
        Set<Long> theNeighbours = new HashSet<>(4);
        game.getTile(this.row+1, this.column).filter(tile -> !WATER.equals(tile.getTerrain().getIcon()))
                .map(Tile::getId).ifPresent(theNeighbours::add);
        game.getTile(this.row-1, this.column).filter(tile -> !WATER.equals(tile.getTerrain().getIcon()))
                .map(Tile::getId).ifPresent(theNeighbours::add);
        game.getTile(this.row, this.column+1).filter(tile -> !WATER.equals(tile.getTerrain().getIcon()))
                .map(Tile::getId).ifPresent(theNeighbours::add);
        game.getTile(this.row, this.column-1).filter(tile -> !WATER.equals(tile.getTerrain().getIcon()))
                .map(Tile::getId).ifPresent(theNeighbours::add);
        return theNeighbours;
    }

    public double getTravelCost() {
        return this.terrain.getTerrainType().getMovementCost();
    }
}
