package eu.borderprinces;

import eu.borderprinces.entities.*;
import eu.borderprinces.map.Map;
import eu.borderprinces.map.MapUtils;
import eu.borderprinces.map.ScenarioLoader;
import eu.borderprinces.map.ScenarioMaps;

import java.util.Scanner;
import java.util.stream.Collectors;

import static eu.borderprinces.BorderPrincesConstants.MONSTER_LAIR;

public class BorderPrinces {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = "";
        Game game = ScenarioLoader.createGame(ScenarioMaps.riverMap);

        while (!input.equals(ConsoleActions.QUIT.action)) {
            System.out.println("\u001B[32m" + Map.printTile(game));
            input = sc.nextLine();
            processInput(input, game, sc);
            endTurn(game);
        }
    }

    private static void processInput(String input, Game game, Scanner sc) {
        input = checkInput(input, game, sc);

        ConsoleActions consoleAction = ConsoleActions.get(input);
        switch (consoleAction) {
            case QUIT -> System.exit(130);
            case DOWN -> game.player.move(game, 1, 0);
            case UP -> game.player.move(game, -1, 0);
            case LEFT -> game.player.move(game, 0, -1);
            case RIGHT -> game.player.move(game, 0, 1);
            case CLEAR_LAIR -> {
                Tile currentTile = game.player.getTile();
                if (MONSTER_LAIR.equals(currentTile.getBuilding().getIcon())) {
                    currentTile.destroyBuilding(game);
                }
            }
            case NEW_GAME -> {
                System.out.println("select one of the following scenarios");
                ScenarioMaps.scenarios.keySet().forEach(System.out::println);
                input = sc.nextLine();
                game = ScenarioLoader.createGame(ScenarioMaps.scenarios.get(input));
            }
            default -> {
            }
        }
    }

    private static String checkInput(String input, Game game, Scanner sc) {
        if (!game.player.isAvailableAction(input)) {
            System.out.println("select one of the following inputs");
            game.player.getActions().stream()
                    .map(ConsoleActions::getAction)
                    .forEach(System.out::println);
            input = sc.nextLine();
            return checkInput(input, game, sc);
        } else {
            return input;
        }
    }

    private static void endTurn(Game game) {
        checkState(game);
        moveUnits(game);
        buildingChecks(game);
    }

    private static void checkState(Game game) {
        if (game.playerBuildings.isEmpty()){
            throw new RuntimeException("YOU LOSE! YOU HAVE LOST ALL VILLAGES!");
        } else if(game.monsterBuildings.isEmpty()){
            throw new RuntimeException("YOU WIN!");
        }
    }

    private static void moveUnits(Game game) {
        game.monsters.forEach(Monster::move);
        game.monsters = game.monsters.stream()
                .filter(monster -> monster.getHealth() > 0)
                .collect(Collectors.toList());
    }

    private static void buildingChecks(Game game) {
game.monsterBuildings.stream()
        .map(Building::getTile)
        .filter(t -> t.getUnit() == null)
        .map(Tile::getBuilding)
        .map(x -> ((Lair) x))
        .forEach(lair -> lair.takeTurn(game));
    }
}
