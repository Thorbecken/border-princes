package eu.borderprinces.map;

import eu.borderprinces.ConsoleActions;
import eu.borderprinces.entities.Player;
import junit.framework.Assert;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;

import static eu.borderprinces.map.MapUtils.putMenuString;

public class Menu {
    static HashMap<Integer, HashMap<Integer, String>> menuMap = new HashMap<>();

    static {
        for (int i = 1; i <= 32; i++) {
            menuMap.put(i, new HashMap<>());
        }

        putMenuString(menuMap, 1, "_____________________");
        putMenuString(menuMap, 2, "| available commands ");
        putMenuString(menuMap, 3, "|                    ");
    }

    private static String menuOption(String quit) {
        Assert.assertTrue(quit.length() <= 17);
        return StringUtils.rightPad(quit, 17);
    }

    public static String menuOption(ConsoleActions consoleActions) {
        return "| - " + menuOption(consoleActions.action);
    }

    public static String healthBar(Player player) {
        return "| - " + menuOption("*".repeat(Math.max(0, player.getHealth())));
    }
}
