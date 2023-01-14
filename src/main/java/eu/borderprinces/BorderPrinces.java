package eu.borderprinces;

import eu.borderprinces.entities.Building;
import eu.borderprinces.entities.Game;
import eu.borderprinces.entities.Tile;
import eu.borderprinces.entities.Unit;
import eu.borderprinces.entities.unit.Player;
import eu.borderprinces.entities.unit.Soldier;
import eu.borderprinces.entities.unit.UnitLogic;
import eu.borderprinces.map.Map;
import eu.borderprinces.map.Menu;
import eu.borderprinces.map.ScenarioLoader;
import eu.borderprinces.map.ScenarioMaps;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static eu.borderprinces.BorderPrincesConstants.*;

public class BorderPrinces {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = "";
        Game game = ScenarioLoader.createGame(ScenarioMaps.riverMap);

        while (!input.equals(ConsoleActions.QUIT.action)) {
            System.out.println("\u001B[32m" + Map.printTile(game));
            input = sc.nextLine();
            game = processInput(input, game, sc);
            endTurn(game);
        }
    }

    private static Game processInput(String input, Game game, Scanner sc) {
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
            case BUILD_VILLAGE -> {
                Tile currentTile = game.player.getTile();
                if (currentTile.getBuilding() == null &&
                        BARE_GROUND.equals(currentTile.getTerrain().getIcon())) {
                    currentTile.createVillage(VILLAGE);
                    game.buildings.add(currentTile.getBuilding());
                }
            }
            case RECRUIT -> recruitOption(game, sc);
            case COMMAND -> commandOption(game, sc);
            case NEW_GAME -> {
                System.out.println("select one of the following scenarios");
                ScenarioMaps.scenarios.keySet().forEach(System.out::println);
                input = sc.nextLine();
                return ScenarioLoader.createGame(ScenarioMaps.scenarios.get(input));
            }
            default -> {
            }
        }
        return game;
    }

    private static void recruitOption(Game game, Scanner sc) {
        Player player = game.player;
        long currentUnits = game.units.stream()
                .filter(unit -> player.getTeamId() == unit.getTeamId())
                .count();
        // player doesn't count
        currentUnits--;
        long currentBuildings = game.buildings.stream()
                .filter(building -> player.getTeamId() == building.getTeamId())
                .count();
        System.out.println("current unit count: " + currentUnits);
        System.out.println("current village count: " + currentBuildings);
        if (currentUnits < currentBuildings) {
            Tile recruitmentTile = player.getTile();
            UnitLogic unitLogic = null;
            while(unitLogic == null){
                Arrays.stream(UnitLogic.values()).forEach( ul -> System.out.println(ul.getSelection() + " for " + ul.getName()));
                System.out.println("current unit count: " + currentUnits);
                unitLogic = UnitLogic.get(sc.nextLine());
            }
            new Soldier(player.getTeamId(), recruitmentTile, player.getIcon(), game, unitLogic);
        } else {
            System.out.println("your kingdom can't support more units on the field");
        }
    }

    private static void commandOption(Game game, Scanner sc) {
        Player player = game.player;
        List<Soldier> units = game.units.stream()
                .filter(unit -> unit instanceof Soldier)
                .map(unit -> ((Soldier) unit))
                .filter(unit -> player.getTeamId() == unit.getTeamId())
                .filter(unit -> unit.getId() != player.getId())
                .sorted(Comparator.comparingLong(Unit::getId))
                .toList();

        System.out.println(Menu.unitBarStart());
        units.forEach(unit -> System.out.println(Menu.unitView(unit)));
        System.out.println(Menu.unitBarEnd());
        Long id = null;
        try {
            id = Long.parseLong(sc.nextLine());
        } catch(NumberFormatException ignored){}
        if (id != null){
            final long finalId = id;
            UnitLogic unitLogic = null;
            while(unitLogic == null){
                Arrays.stream(UnitLogic.values()).forEach( ul -> System.out.println(ul.getSelection() + " for " + ul.getName()));
                unitLogic = UnitLogic.get(sc.nextLine());
            }
            final UnitLogic finalUnitLogic = unitLogic;
            units.stream()
                    .filter(unit -> finalId == unit.getId())
                    .findFirst()
                    .ifPresent(unit -> unit.setUnitLogic(finalUnitLogic));
        }
    }

    private static String checkInput(String input, Game game, Scanner sc) {
        if (ConsoleActions.options(game.player.getTile()).stream()
                .noneMatch(options -> options.equals(input))) {
            System.out.println("select one of the following inputs");
            ConsoleActions.options(game.player.getTile())
                    .forEach(System.out::println);
            return checkInput(sc.nextLine(), game, sc);
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
        if (game.buildings.stream().map(Building::getTeamId).noneMatch(b -> b.equals(TEAM_PLAYER))) {
            throw new RuntimeException("YOU LOSE! YOU HAVE LOST ALL VILLAGES!");
        } else if (game.buildings.stream().map(Building::getTeamId).allMatch(b -> b.equals(TEAM_PLAYER))) {
            throw new RuntimeException("YOU WIN!");
        }
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    private static void moveUnits(Game game) {
        game.units.forEach(Unit::takeAction);
        game.units = game.units.stream()
                .filter(unit -> unit.getHealth() > 0)
                .collect(Collectors.toList());
        game.units.forEach(unit -> {
            if (!game.units.contains(unit.getCurrentTarget()) && !game.buildings.contains(unit.getCurrentTarget())) {
                unit.setCurrentTarget(null);
            }
        });
    }

    private static void buildingChecks(Game game) {
        game.buildings.forEach(building -> building.takeTurn(game));
    }
}
