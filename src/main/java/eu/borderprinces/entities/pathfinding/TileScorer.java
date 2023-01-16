package eu.borderprinces.entities.pathfinding;

import eu.borderprinces.entities.Tile;

public class TileScorer implements Scorer<Tile> {
    @Override
    public double computeCost(Tile from, Tile to) {
        return from.getDistance(to);
    }

}