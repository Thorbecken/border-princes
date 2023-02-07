package eu.borderprinces.map;

import eu.borderprinces.entities.Game;
import eu.borderprinces.entities.Tile;
import eu.borderprinces.entities.building.Capitol;
import eu.borderprinces.entities.unit.Prince;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static eu.borderprinces.BorderPrincesConstants.*;

public class ScenarioLoader {


    public static Game createGame(HashMap<Integer, HashMap<Integer, String>> scenario) {
        Random random = new Random();
        Game game = new Game(MapUtils.toScenario(scenario));
        List<Tile> bareGroundTiles = game.scenario.values().stream()
                .flatMap(x -> x.values().stream())
                .filter(x -> x.getTerrain().getIcon().equals(BARE_GROUND))
                .toList();

        List<Tile> playerStartingTiles = List.of(bareGroundTiles.get(random.nextInt(bareGroundTiles.size())));
        List<Tile> monsterTiles = bareGroundTiles.stream()
                .filter(monsterTile -> !playerStartingTiles.contains(monsterTile))
                .toList();

        generatePlayer(playerStartingTiles, game);
        generatePlayerBuildings(playerStartingTiles, game);
        generateLairs(monsterTiles, game);

        return game;
    }

    private static void generatePlayer(List<Tile> playerStartingTiles, Game game) {
        Random random = new Random();
        Tile StartingTile = playerStartingTiles.get(random.nextInt(playerStartingTiles.size()));
        new Prince(TEAM_PLAYER, StartingTile, PLAYER, game);
    }

    private static void generatePlayerBuildings(List<Tile> playerStartingTiles, Game game) {
        playerStartingTiles.forEach(capitol -> {
            capitol.createCaptiol();
            game.buildings.add(capitol.getBuilding());
            game.capitol = ((Capitol) capitol.getBuilding());
        });
    }

    private static void generateLairs(List<Tile> monsterStartingTiles, Game game) {
        monsterStartingTiles.forEach(lair -> {
            lair.createLair(TEAM_MONSTERS);
            game.buildings.add(lair.getBuilding());
        });
    }
}
