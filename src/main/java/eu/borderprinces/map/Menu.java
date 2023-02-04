package eu.borderprinces.map;

import eu.borderprinces.entities.Unit;
import eu.borderprinces.entities.unit.Prince;
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

    private static String paddedMenuOption(String action, int padding) {
        Assert.assertTrue(action.length() <= padding);
        return StringUtils.rightPad(action, padding);
    }

    public static String menuOption(String action) {
        return "| - " + paddedMenuOption(action,17);
    }

    public static String healthBar(Prince prince) {
        return "| - " + paddedMenuOption("*".repeat(Math.max(0, prince.getHealth())),17);
    }

    public static String unitBarStart(){
        String returnValue ="________________________________________________\r\n";
        returnValue += "| id  ";
        returnValue += "| row ";
        returnValue += "| column ";
        returnValue += "| current command         ";
        returnValue += "|";

        return returnValue;
    }

    public static String unitBarEnd(){
        String returnValue ="------------------------------------------------\r\n";
        returnValue += "please enter id of unit to command";

        return returnValue;
    }

    public static String unitView(Unit unit) {
        String returnValue = "| " + paddedMenuOption(String.valueOf(unit.getId()),4);
        returnValue += "| " + paddedMenuOption(String.valueOf(unit.getTile().getRow()),4);
        returnValue += "| " + paddedMenuOption(String.valueOf(unit.getTile().getColumn()),7);
        returnValue += "| " + paddedMenuOption(String.valueOf(unit.getUnitLogic().getName()),24);
        returnValue += "|";

        return returnValue;
    }
}
