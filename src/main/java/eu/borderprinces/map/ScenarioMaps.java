package eu.borderprinces.map;

import java.util.HashMap;

import static eu.borderprinces.map.MapUtils.putString;

public class ScenarioMaps {
    public static HashMap<String, HashMap<Integer, HashMap<Integer, String>>> scenarios = new HashMap<>();
    public static HashMap<Integer, HashMap<Integer, String>> riverMap = new HashMap<>();
    public static HashMap<Integer, HashMap<Integer, String>> coastalMap = new HashMap<>();

    static {
        scenarios.put("river", riverMap);
        scenarios.put("coastal", coastalMap);
    }

    // ^ = mountain
    // ~ = river
    // = = bridge
    // - = plain
    // * = forest
    // _ = bare ground
    // π = monster lair
    // n = village
    // M = monster
    // | = player

    static {
        for (int i = 1; i <= 32; i++) {
            riverMap.put(i, new HashMap<>());
        }

        putString(riverMap, 1, "^^^^~-^^^^^^^^^^^^^^^^^^^^^^^^^^");
        putString(riverMap, 2, "^^^^~--^^^^^^^^^*****^^^^^^^^^^^");
        putString(riverMap, 3, "^^^^~~-_^^^^**************^^^^^^");
        putString(riverMap, 4, "^^^^^~~--^^*************-----^^^");
        putString(riverMap, 5, "^^^^^^~------******-_****-------");
        putString(riverMap, 6, "^^^^^^~-------**----*****-------");
        putString(riverMap, 7, "^^^^^^~~----------*****---------");
        putString(riverMap, 8, "^^^^^^^~~---------------------^^");
        putString(riverMap, 9, "^^^^^^^^~~-----------------^^^^^");
        putString(riverMap, 10, "^^^^_-^^^~~=~~~------^^^^^^^^^^^");
        putString(riverMap, 11, "^^^^^---------~~--------^^^^^^^^");
        putString(riverMap, 12, "**^^^^^^-------~~~-----------_^^");
        putString(riverMap, 13, "-****^^^^--------~~~*******-----");
        putString(riverMap, 14, "--***^^^-----------~~~*******---");
        putString(riverMap, 15, "---*^^^------**----**~~~********");
        putString(riverMap, 16, "---^^-------****----***~~*******");
        putString(riverMap, 17, "------------****-----***~~~~=~~~");
        putString(riverMap, 18, "----------*******--*************");
        putString(riverMap, 19, "----------**********************");
        putString(riverMap, 20, "~~~---------********************");
        putString(riverMap, 21, "--~~~~~~----*****************-**");
        putString(riverMap, 22, "-------~~-----***********_***---");
        putString(riverMap, 23, "--------~~~-----************----");
        putString(riverMap, 24, "--**------~------**********-----");
        putString(riverMap, 25, "******----~~~~---********-------");
        putString(riverMap, 26, "*******------~~~~~~~~**---------");
        putString(riverMap, 27, "********_--------***~~~~~~~-----");
        putString(riverMap, 28, "*************---*****-----~~~~~~");
        putString(riverMap, 29, "************************--------");
        putString(riverMap, 30, "***********************---------");
        putString(riverMap, 31, "*********************-----------");
        putString(riverMap, 32, "********************------------");

        for (int i = 1; i <= 32; i++) {
            coastalMap.put(i, new HashMap<>());
        }

         putString(coastalMap, 1, "~~~---------------***_----------");
         putString(coastalMap, 2, "~~~-----------***********-------");
         putString(coastalMap, 3, "~~~~--------*****^^^^^^^**------");
         putString(coastalMap, 4, "~~~~~~-----***^^^^^******-------");
         putString(coastalMap, 5, "~~~~~~----****^^*********-------");
         putString(coastalMap, 6, "~~~~--------*********^^*--------");
         putString(coastalMap, 7, "~~~~----------***^^^^^**--------");
         putString(coastalMap, 8, "~~~~--------****^^*****-------_~");
         putString(coastalMap, 9, "~~~~-------****^^****_-------~~~");
        putString(coastalMap, 10, "~~-----*******^^***--------~~~~~");
        putString(coastalMap, 11, "~~--*************---------~~~~~-");
        putString(coastalMap, 12, "~~*******_------------~~~~~~~---");
        putString(coastalMap, 13, "~~*********------~~=~~~~~~~-----");
        putString(coastalMap, 14, "~~*********---~~~~~=~~~~~-------");
        putString(coastalMap, 15, "~~~****---~~~~~~~~~=~~----------");
        putString(coastalMap, 16, "~~~~~~~~~~~~~~~~----------------");
        putString(coastalMap, 17, "~~~~~~~~~~~~~~-----**********---");
        putString(coastalMap, 18, "~~~~~~~~~~~-------******_****---");
        putString(coastalMap, 19, "~~~~~~~~~~--^^-************-----");
        putString(coastalMap, 20, "~~~~~~~~~~-^^^^^--**********----");
        putString(coastalMap, 21, "~~~~~~~~~~-^^^^^^^----******----");
        putString(coastalMap, 22, "~~~~~~~~~~--^^^^^^_----*****----");
        putString(coastalMap, 23, "~~~~~~~~~~~-^^^^^^^-**********--");
        putString(coastalMap, 24, "~~~~~~~~~~~---^^^^^^--********-^");
        putString(coastalMap, 25, "~~~~~~~~~~~~~----------****-^^^^");
        putString(coastalMap, 26, "~~~~~~--~~~~~~~~~~~~----------^^");
        putString(coastalMap, 27, "~~~~~-^^**-~~~~~~~~~~~~~~~------");
        putString(coastalMap, 28, "~~~~~~-^^^*-~~~~~~~~~~~~~~~~~~~-");
        putString(coastalMap, 29, "~~~~~~---^^*-~~~~~~~~~~~~~~~~~~~");
        putString(coastalMap, 30, "~~~~~~~~~--^-~~~~~~~~~~~~~~~~~~~");
        putString(coastalMap, 31, "~~~~~~~~~~~--~~~~~~~~~~~~~~~~~~~");
        putString(coastalMap, 32, "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }
}
