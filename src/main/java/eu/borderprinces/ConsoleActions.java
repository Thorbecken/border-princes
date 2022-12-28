package eu.borderprinces;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public enum ConsoleActions {
    QUIT("quit"),
    NEW_GAME("new game"),
    LEFT("left"),
    RIGHT("right"),
    UP("up"),
    DOWN("down"),
    CLEAR_LAIR("clear lair"),
    WAIT("wait");
    public final String action;
    public static List<String> options(){
        return Arrays.stream(ConsoleActions.values()).map(x -> x.action).toList();
    }
    public static boolean contains(String value){
        return options().stream().anyMatch(x -> x.equals(value));
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public static ConsoleActions get(String value){
        return Arrays.stream(ConsoleActions.values()).filter(x -> x.action.equals(value)).findFirst().get();
    }
}
