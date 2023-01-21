package eu.borderprinces.entities.pathfinding.scorer;

import eu.borderprinces.entities.pathfinding.GraphNode;

public interface Scorer<T extends GraphNode> {
    double computeCost(T from, T to);
}