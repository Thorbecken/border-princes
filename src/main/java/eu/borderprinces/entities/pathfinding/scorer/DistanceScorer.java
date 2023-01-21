package eu.borderprinces.entities.pathfinding.scorer;

import eu.borderprinces.entities.Tile;

public class DistanceScorer implements Scorer<Tile> {
    @Override
    public double computeCost(Tile from, Tile to) {
        return (from.getDistance(to));
    }

}