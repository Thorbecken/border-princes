package eu.borderprinces.entities.unit;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UnitLogic {

    DEFEND("Defend","d"),
    PATROL("Patrol","p"),
    SEARCH_AND_DESTROY("Search and destroy","s"),
    FARM("Farming", "f"),
    BUILD_VILLAGE("Build village", "b");

    private final String name;
    private final String selectionShortcut;
}
