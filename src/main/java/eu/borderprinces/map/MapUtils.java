package eu.borderprinces.map;

import eu.borderprinces.entities.Tile;

import java.util.HashMap;

public class MapUtils {

    static void putString(HashMap<Integer, HashMap<Integer, String>> map, Integer row, String columns) {
        for (int i = 1; i <= columns.length(); i++) {
            map.get(row).put(i, columns.substring(i - 1, i));
        }
    }

    static void putMenuString(HashMap<Integer, HashMap<Integer, String>> map, Integer row, String columns) {
        for (int i = 1; i <= columns.length(); i++) {
            map.get(row).put(i, columns.substring(i - 1, i));
        }
    }

    public static HashMap<Integer, HashMap<Integer, Tile>> toScenario(HashMap<Integer, HashMap<Integer, String>> map) {
        HashMap<Integer, HashMap<Integer, Tile>> output = new HashMap<>();
        for (int row = 1; row <= map.values().size(); row++) {
            HashMap<Integer, String> rowMap = map.get(row);
            output.put(row, new HashMap<>());
            for (int column = 1; column <= rowMap.values().size(); column++) {
                output.get(row).put(column, new Tile(map.get(row).get(column), row, column));
            }
        }
        return output;
    }
}
