package eu.borderprinces.entities;

import eu.borderprinces.entities.building.Lair;
import eu.borderprinces.entities.building.Village;
import eu.borderprinces.map.MapColorUtils;
import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

import static eu.borderprinces.BorderPrincesConstants.*;

public class Tile {

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
        this.row = row;
        this.column = column;
        this.units = new ArrayList<>();
        this.controlingTeam = null;

        setTerrain(terrain);
    }

    private void setCurrentIcon(String currentIcon) {
        this.currentIcon = MapColorUtils.colored(currentIcon);
    }

    private void setTerrain(String terrain) {
        this.terrain = new Terrain(row, column, terrain);
        if (this.building == null && this.units.isEmpty()) {
            setCurrentIcon(terrain);
        }
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public void createLair(String building, long teamId) {
        this.building = new Lair(teamId, this, building, MONSTER);
        setBuilding(this.building);
    }

    public void createVillage(String building) {
        this.building = new Village(TEAM_PLAYER, this, building);
        setBuilding(this.building);
    }

    public void setBuilding(Building building) {
        if (building == null && !this.units.isEmpty()) {
            setCurrentIcon(this.units.get(0).getIcon());
        } else if (building != null) {
            setCurrentIcon(building.getIcon());
        } else {
            setCurrentIcon(this.terrain.getIcon());
        }
    }

    public void addUnit(Unit unit) {
        if (this.units.isEmpty()) {
            this.controlingTeam = unit.getTeamId();
        } else if (!this.controlingTeam.equals(unit.getTeamId())) {
            throw new RuntimeException("Tile is owned by another team");
        }

        this.units.add(unit);
        setCurrentIcon(unit.getIcon());
    }

    public void removeUnit(Unit unit) {
        this.units.remove(unit);
        if(units.isEmpty()){
            this.controlingTeam = null;
        }

        if (this.building != null) {
            setCurrentIcon(this.building.getIcon());
        } else {
            setCurrentIcon(this.terrain.getIcon());
        }
    }

    public boolean noUnits() {
        return units.isEmpty();
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
        this.setBuilding(null);
    }

    public String getIcon() {
        return currentIcon;
    }

    public int getDistance(Tile targetTile, Tile currentTile) {
        int rd = targetTile.getRow() - currentTile.getRow();
        int cd = targetTile.getColumn() - currentTile.getColumn();
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
}
