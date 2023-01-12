package eu.borderprinces.entities.unit;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum UnitLogic {

    DEFEND("Defend","d"),
    PATROL("Patrol","p"),
    SEARCH_AND_DESTROY("Search and destroy","s");

    private final String name;
    private final String selection;

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public static UnitLogic get(String value) {
        return Arrays.stream(UnitLogic.values()).filter(x -> x.selection.equals(value)).findFirst().get();
    }
}
