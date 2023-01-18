package eu.borderprinces.entities;

import eu.borderprinces.entities.building.Lair;
import eu.borderprinces.entities.building.Village;
import eu.borderprinces.entities.pathfinding.GraphNode;
import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        return this.controlingTeam == null || teamId.equals(this.controlingTeam);
    }

    private Long controlingTeam;
    private String currentIcon;
    private Terrain terrain;
    private Building building;
    private final List<Unit> units;

    public Tile(String terrain, int row, int column) {
        this.id = nextId++;
        this.row = row;
        this.column = column;
        this.units = new ArrayList<>();
        this.controlingTeam = null;

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

    public void createLair(String building, long teamId) {
        this.building = new Lair(teamId, this, building, MONSTER);
        setCurrentIcon();
    }

    public void createVillage(String building) {
        this.building = new Village(TEAM_PLAYER, this, building);
        setCurrentIcon();
    }

    public void addUnit(Unit unit) {
        if (this.units.isEmpty()) {
            this.controlingTeam = unit.getTeamId();
        } else if (!this.controlingTeam.equals(unit.getTeamId())) {
            throw new RuntimeException("Tile is owned by another team");
        }

        this.units.add(unit);
        setCurrentIcon();
    }

    public void removeUnit(Unit unit) {
        this.units.remove(unit);
        if (units.isEmpty()) {
            this.controlingTeam = null;
        }
        setCurrentIcon();
    }

    public Building getBuilding() {
        return building;
    }

    public void destroyBuilding(Game game) {
        game.buildings.remove(this.building);
        if (game.buildings.stream().map(Building::getTeamId).noneMatch(b -> b.equals(TEAM_PLAYER))) {
            throw new RuntimeException("YOU LOSE! YOU HAVE LOST ALL VILLAGES!");
        }
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
