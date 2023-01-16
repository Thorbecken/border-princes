package eu.borderprinces.entities.pathfinding;

import eu.borderprinces.entities.Game;
import eu.borderprinces.entities.Tile;

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
        routeFinder = new RouteFinder<>(worldMap, new TileScorer(), new TileScorer());
    }

    public List<Tile> pathfind(Tile from, Tile to) {
        if(from != null && to != null) {
            return routeFinder.findRoute(from, to);
        } else {
            return new ArrayList<>();
        }
    }
}