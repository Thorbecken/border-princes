package eu.borderprinces.entities;

import eu.borderprinces.entities.building.Village;
import lombok.Data;
import lombok.NonNull;

import static eu.borderprinces.BorderPrincesConstants.TEAM_PLAYER;

@Data
public abstract class Building implements Target {

    private Long teamId;
    private final Tile tile;

    private final String icon;
    private final Color color;

    public Building(long teamId, @NonNull Tile tile, @NonNull String icon, @NonNull Color color) {
        this.teamId = teamId;
        this.tile = tile;
        this.icon = icon;
        this.color = color;
    }

    public abstract void takeTurn(Game game);
    public void destroy(Game game){
        game.buildings.remove(this);
        if (game.buildings.stream()
                .filter(x -> x instanceof Village)
                .map(Building::getTeamId)
                .noneMatch(b -> b.equals(TEAM_PLAYER))) {
            throw new RuntimeException("YOU LOSE! YOU HAVE LOST ALL VILLAGES!");
        }
    }

    @Override
    public String toString() {
        return "Building{" +
                "icon='" + icon + '\'' +
                '}';
    }

    public String getColored() {
        return this.color.getCode() + this.icon;
    }
}
