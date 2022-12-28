package eu.borderprinces.map;

import eu.borderprinces.entities.*;

import java.util.HashMap;

import static eu.borderprinces.BorderPrincesConstants.*;

public class ScenarioLoader {


    public static Game createGame(HashMap<Integer, HashMap<Integer, String>> scenario) {
        Game game = new Game(MapUtils.toScenario(scenario));
        generatePlayer(game);
        generatePlayerBuildings(game);
        generateLairs(game);
//        generateMonsters(game);

        return game;
    }

    private static void generatePlayer(Game game) {
        Tile startingTile = game.scenario.values()
                .stream().flatMap(x -> x.values().stream())
                .filter(x -> x.getBuilding() != null)
                .filter(x -> VILLAGE.equals(x.getBuilding().getIcon()))
                .findAny()
                .orElseThrow();
        new Player(startingTile, PLAYER,game);
    }

    private static void generatePlayerBuildings(Game game) {
        game.scenario.values().stream()
                .flatMap(x -> x.values().stream())
                .filter(x -> x.getBuilding() != null)
                .filter(x -> VILLAGE.equals(x.getBuilding().getIcon()))
                .forEach(village -> game.playerBuildings.add(village.getBuilding()));
    }

    private static void generateLairs(Game game) {
        game.scenario.values().stream()
                .flatMap(x -> x.values().stream())
                .filter(x -> x.getBuilding() != null)
                .filter(x -> MONSTER_LAIR.equals(x.getBuilding().getIcon()))
                .forEach(lair -> game.monsterBuildings.add(((Lair) lair.getBuilding())));
    }

    private static void generateMonsters(Game game) {
        game.monsterBuildings.stream()
                .map(Building::getTile)
                .forEach(lair -> lair.setUnit(new Monster(lair, MONSTER, game)));
    }
}
