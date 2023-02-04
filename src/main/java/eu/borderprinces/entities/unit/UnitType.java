package eu.borderprinces.entities.unit;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum UnitType {

    SOLDIER("Soldier", "s", true, List.of(UnitLogic.DEFEND, UnitLogic.PATROL, UnitLogic.SEARCH_AND_DESTROY)),
    PRINCE("Prince", "p", false, List.of(UnitLogic.DEFEND, UnitLogic.PATROL, UnitLogic.SEARCH_AND_DESTROY)),
    MONSTER("Monster", "m", false, List.of(UnitLogic.DEFEND, UnitLogic.PATROL, UnitLogic.SEARCH_AND_DESTROY)),
    FARMER("Farmer", "f", true, List.of(UnitLogic.FARM));

    private final String name;
    private final String selectionShortcut;
    private final boolean recruitable;
    private final List<UnitLogic> unitLogicList;

    public static UnitType get(String value) {
        return Arrays.stream(UnitType.values()).filter(x -> x.selectionShortcut.equals(value)).findFirst().orElse(null);
    }

    public static UnitType getRecruitable(String value) {
        return Arrays.stream(UnitType.values())
                .filter(UnitType::isRecruitable)
                .filter(x -> x.selectionShortcut.equals(value))
                .findFirst().orElse(null);
    }

    public UnitLogic getUnitLogic(String value) {
        return unitLogicList.stream().filter(x -> x.getSelectionShortcut().equals(value)).findFirst().orElse(null);
    }

    public static List<UnitType> recruitables(){
        return Arrays.stream(UnitType.values())
                .filter(UnitType::isRecruitable)
                .collect(Collectors.toList());
    }
}
