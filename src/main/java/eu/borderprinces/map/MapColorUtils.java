package eu.borderprinces.map;

public class MapColorUtils {
    public static final String TEXT_GREEN = "\u001B[32m";
    public static final String TEXT_BLUE = "\u001B[36m";
    public static final String TEXT_WHITE = "\u001B[37m";
    public static final String TEXT_RED = "\u001B[31m";
    public static final String TEXT_PURPLE = "\u001B[35m";
    public static final String TEXT_YELLOW = "\u001B[33m";

    private static final String m = TEXT_WHITE + "^";
    private static final String r = TEXT_BLUE + "~";
    private static final String p = TEXT_YELLOW + "-";
    private static final String f = TEXT_GREEN + "*";
    private static final String l = TEXT_RED + "π";
    private static final String n = TEXT_PURPLE + "n";
    private static final String M = TEXT_RED + "M";
    private static final String pl = TEXT_PURPLE + "|";

    public static String colored(String input) {
        String output;
        output = input.replace("*", f);
        output = output.replace("-", p);
        output = output.replace("|", pl);
        output = output.replace("~", r);
        output = output.replace("M", M);
        output = output.replace("π", l);
        output = output.replace("n", n);
        output = output.replace("^", m);

        return output;
    }

}
