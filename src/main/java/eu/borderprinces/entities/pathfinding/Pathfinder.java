package eu.borderprinces.entities.pathfinding;

import eu.borderprinces.entities.Game;
import eu.borderprinces.entities.Tile;
import eu.borderprinces.entities.pathfinding.scorer.DistanceScorer;
import eu.borderprinces.entities.pathfinding.scorer.MovementScorer;

import java.util.*;

public class Pathfinder {

    private final RouteFinder<Tile> routeFinder;

    public Pathfinder(Game game) {
        Set<Tile> tiles = new HashSet<>();
        Map<Long, Set<Long>> connections = new HashMap<>();

        game.scenario.values().stream()
                .flatMap(x -> x.values().stream())
                .forEach(tiles::add);
        tiles.forEach(tile -> connections.put(tile.getId(), tile.getLandNeighbours(game)));


        Graph<Tile> worldMap = new Graph<>(tiles, connections);
        routeFinder = new RouteFinder<>(worldMap, new MovementScorer(), new DistanceScorer());
    }

    public List<Tile> pathfind(Tile from, Tile to) {
        if(from != null && to != null) {
            List<Tile> route = routeFinder.findRoute(from, to);
            // remove the first tile, because that's the starting tile.
            route.remove(0);
            return route;
        } else {
            return new ArrayList<>();
        }
    }
}
