package eu.borderprinces.map;

import eu.borderprinces.entities.Game;
import eu.borderprinces.entities.Player;
import eu.borderprinces.entities.Tile;

import java.util.HashMap;

import static eu.borderprinces.map.Menu.menuMap;

public class Map {

    public static final String MENU_COLOR = "\u001B[34m";
    public static String print(HashMap<Integer, HashMap<Integer, String>> scenario) {
        StringBuilder rv = new StringBuilder();
        for (int rowNumber = 1; rowNumber <= scenario.values().size(); rowNumber++) {
            printLine(rowNumber, scenario.get(rowNumber), rv);
        }
        return rv.toString();
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    static void printLine(int rowNumber, HashMap<Integer, String> row, StringBuilder stringBuilder) {
        stringBuilder.append(row.values().stream().reduce((x, y) -> x + "  " + y).get());
        stringBuilder.append("          " + MENU_COLOR);
        stringBuilder.append(menuMap.get(rowNumber).values().stream().reduce((x, y) -> x + y).get());
        stringBuilder.append("\n");
    }

    public static String printTile(Game game) {
        StringBuilder rv = new StringBuilder();
        for (int rowNumber = 1; rowNumber <= game.scenario.values().size(); rowNumber++) {
            printTileLine(rowNumber, game.scenario.get(rowNumber), rv, game.player);
        }
        return rv.toString();
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    static void printTileLine(int rowNumber, HashMap<Integer, Tile> row, StringBuilder stringBuilder, Player player) {
        stringBuilder.append(row.values().stream()
                .map(Tile::getIcon)
                .reduce((x, y) -> x + "  " + y)
                .get());
        stringBuilder.append("          " + MENU_COLOR);
        if (rowNumber < 3) {
            stringBuilder.append(menuMap.get(rowNumber).values().stream().reduce((x, y) -> x + y).get());
        } else if (rowNumber == 3){
            stringBuilder.append(Menu.healthBar(player));
        } else if (rowNumber < (player.getActions().size() + 3)) {
            stringBuilder.append(Menu.menuOption(player.getActions().stream().toList().get(rowNumber -3)));
        } else {
            stringBuilder.append(menuMap.get(3).values().stream().reduce((x, y) -> x + y).get());
        }
        stringBuilder.append("\n");
    }

}
