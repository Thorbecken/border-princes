package eu.borderprinces.entities.unit;

import eu.borderprinces.BorderPrincesConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum UnitType {

    SOLDIER("Soldier", "s", true, BorderPrincesConstants.SOLDIER, List.of(UnitLogic.DEFEND, UnitLogic.PATROL, UnitLogic.SEARCH_AND_DESTROY)),
    PRINCE("Prince", "p", false, BorderPrincesConstants.PLAYER, List.of(UnitLogic.DEFEND, UnitLogic.PATROL, UnitLogic.SEARCH_AND_DESTROY)),
    MONSTER("Monster", "m", false, BorderPrincesConstants.MONSTER, List.of(UnitLogic.DEFEND, UnitLogic.PATROL, UnitLogic.SEARCH_AND_DESTROY)),
    FARMER("Farmer", "f", true, BorderPrincesConstants.FARMER, List.of(UnitLogic.FARM)),
    BUILDER("Builder", "b", true, BorderPrincesConstants.BUILDER, List.of(UnitLogic.BUILD_VILLAGE));

    private final String name;
    private final String selectionShortcut;
    private final boolean recruitable;
    private final String icon;
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
