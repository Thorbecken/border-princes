package eu.borderprinces.entities;

import eu.borderprinces.map.MapColorUtils;
import lombok.Getter;
import lombok.NonNull;

import static eu.borderprinces.BorderPrincesConstants.TEAM_MONSTERS;
import static eu.borderprinces.BorderPrincesConstants.TEAM_PLAYER;

public class Tile {

    @Getter
    @NonNull
    private final Integer row;
    @Getter
    @NonNull
    private final Integer column;
    private String currentIcon;
    private Terrain terrain;
    private Building building;
    private Unit unit;

    public Tile(String terrain, int row, int column) {
        setTerrain(terrain);
        this.row = row;
        this.column = column;
    }

    private void setCurrentIcon(String currentIcon) {
        this.currentIcon = MapColorUtils.colored(currentIcon);
    }

    private void setTerrain(String terrain) {
        this.terrain = new Terrain(row, column, terrain);
        if (this.building == null && this.unit == null) {
            setCurrentIcon(terrain);
        }
    }

    public void createBuilding(String building) {
        this.building = new Building(TEAM_PLAYER,this, building);
        setBuilding(this.building);
    }

    public void createLair(String building) {
        this.building = new Lair(TEAM_MONSTERS, this, building);
        setBuilding(this.building);
    }

    public void setBuilding(Building building) {
        if (this.unit != null) {
            setCurrentIcon(this.unit.getIcon());
        } else if (this.building != null) {
            setCurrentIcon(building.getIcon());
        } else {
            setCurrentIcon(this.terrain.getIcon());
        }
    }

    public void setUnit(Unit unit) {
        this.unit = unit;

        if (unit != null) {
            setCurrentIcon(unit.getIcon());
        } else if (this.building != null) {
            setCurrentIcon(this.building.getIcon());
        } else {
            setCurrentIcon(this.terrain.getIcon());
        }
    }

    public Building getBuilding() {
        return building;
    }

    public Unit getUnit() {
        return unit;
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
                ", unit=" + unit +
                '}';
    }
}
