package eu.borderprinces;

import eu.borderprinces.entities.Building;
import eu.borderprinces.entities.Game;
import eu.borderprinces.entities.Tile;
import eu.borderprinces.entities.Unit;
import eu.borderprinces.entities.building.Capitol;
import eu.borderprinces.entities.building.RecruitmentOrder;
import eu.borderprinces.entities.unit.Prince;
import eu.borderprinces.entities.unit.UnitLogic;
import eu.borderprinces.entities.unit.UnitType;
import eu.borderprinces.map.Map;
import eu.borderprinces.map.Menu;
import eu.borderprinces.map.ScenarioLoader;
import eu.borderprinces.map.ScenarioMaps;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import static eu.borderprinces.BorderPrincesConstants.*;

public class BorderPrinces {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = "";
        Game game = ScenarioLoader.createGame(ScenarioMaps.coastalMap);

        while (!input.equals(ConsoleActions.QUIT.action)) {
            System.out.println("\u001B[32m" + Map.printTile(game));
            input = sc.nextLine();
            game = processInput(input, game, sc);
            endTurn(game);
        }
    }

    private static Game processInput(String input, Game game, Scanner sc) {
        if (game.prince.getActionPoints() <= 0) {
            System.out.println("You have no actionpoints left, please wait and press enter.");
            sc.nextLine();
            return game;
        }
        input = checkInput(input, game, sc);

        ConsoleActions consoleAction = ConsoleActions.get(input);
        switch (consoleAction) {
            case QUIT -> System.exit(130);
            case DOWN -> game.prince.move(game, 1, 0);
            case UP -> game.prince.move(game, -1, 0);
            case LEFT -> game.prince.move(game, 0, -1);
            case RIGHT -> game.prince.move(game, 0, 1);
            case CLEAR_LAIR -> {
                Tile currentTile = game.prince.getTile();
                if (MONSTER_LAIR.equals(currentTile.getBuilding().getIcon())) {
                    currentTile.destroyBuilding(game);
                }
            }
            case BUILD_VILLAGE -> {
                Tile currentTile = game.prince.getTile();
                if (currentTile.getBuilding() == null &&
                        BARE_GROUND.equals(currentTile.getTerrain().getIcon())) {
                    currentTile.createVillage();
                    game.buildings.add(currentTile.getBuilding());
                }
            }
            case SOW_GRAIN_FIELD -> {
                Tile currentTile = game.prince.getTile();
                if (currentTile.getBuilding() == null &&
                        FERTILE_GROUND.equals(currentTile.getTerrain().getIcon())) {
                    currentTile.createGrainField(game);
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
            default -> game.prince.addActionPoint();
        }
        game.prince.removeActionPoint();
        return game;
    }

    private static void recruitOption(Game game, Scanner sc) {
        Capitol capitol = game.capitol;
        capitol.currentOrders().forEach(System.out::println);

        Integer priority = null;
        Long unitNumber = null;
        UnitType unitType = null;
        UnitLogic unitLogic = null;


        while (priority == null) {
            System.out.println("Please enter priority");
            try {
                priority = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException ignore){}
        }
        while (unitNumber == null) {
            System.out.println("Please enter number of units");
            try {
                unitNumber = Long.parseLong(sc.nextLine());
            } catch (NumberFormatException ignore){}
        }
        while (unitType == null) {
            UnitType.recruitables().forEach(ul -> System.out.println(ul.getSelectionShortcut() + " for " + ul.getName()));
            unitType = UnitType.getRecruitable(sc.nextLine());
        }
        while (unitLogic == null) {
            unitType.getUnitLogicList().forEach(ul -> System.out.println(ul.getSelectionShortcut() + " for " + ul.getName()));
            unitLogic = unitType.getUnitLogic(sc.nextLine());
        }

        capitol.addOrder(new RecruitmentOrder(priority, unitNumber, unitType, unitLogic));
    }

    private static void commandOption(Game game, Scanner sc) {
        Prince prince = game.prince;
        List<Unit> units = game.units.stream()
                .filter(unit -> prince.getTeamId() == unit.getTeamId())
                .filter(unit -> unit.getId() != prince.getId())
                .sorted(Comparator.comparingLong(Unit::getId))
                .toList();

        System.out.println(Menu.unitBarStart());
        units.forEach(unit -> System.out.println(Menu.unitView(unit)));
        System.out.println(Menu.unitBarEnd());
        Long id = null;
        try {
            id = Long.parseLong(sc.nextLine());
        } catch (NumberFormatException ignored) {
        }

        if (id != null) {
            final long finalId = id;
            final Optional<Unit> optionalUnit = units.stream()
                    .filter(unit -> finalId == unit.getId())
                    .findFirst();
            if (optionalUnit.isPresent()) {
                Unit unit = optionalUnit.get();
                UnitLogic unitLogic = null;
                while (unitLogic == null) {
                    unit.getUnitType().getUnitLogicList().forEach(ul -> System.out.println(ul.getSelectionShortcut() + " for " + ul.getName()));
                    unitLogic = unit.getUnitType().getUnitLogic(sc.nextLine());
                }
                unit.setUnitLogic(unitLogic);
            }
        }
    }

    private static String checkInput(String input, Game game, Scanner sc) {
        if (ConsoleActions.options(game.prince.getTile()).stream()
                .noneMatch(options -> options.equals(input))) {
            System.out.println("select one of the following inputs");
            ConsoleActions.options(game.prince.getTile())
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
        addActionPoints(game);
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
        game.units.stream()
                .filter(unit -> unit.getActionPoints() > 0)
                .forEach(unit -> {
                    if (!game.units.contains(unit.getCurrentTarget()) && !game.buildings.contains(unit.getCurrentTarget())) {
                        unit.setCurrentTarget(null);
                    }
                });
    }

    private static void buildingChecks(Game game) {
        game.buildings.forEach(building -> building.takeTurn(game));
    }

    private static void addActionPoints(Game game) {
        game.units.forEach(Unit::addActionPoint);
    }
}
