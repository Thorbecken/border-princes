package eu.borderprinces;

import eu.borderprinces.entities.Building;
import eu.borderprinces.entities.Terrain;
import eu.borderprinces.entities.Tile;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static eu.borderprinces.BorderPrincesConstants.*;

@Getter
@AllArgsConstructor
public enum ConsoleActions {
    QUIT("quit",
            tile -> true),
    NEW_GAME("new game",
            tile -> true),
    LEFT("left",
            tile -> true),
    RIGHT("right",
            tile -> true),
    UP("up",
            tile -> true),
    DOWN("down",
            tile -> true),
    CLEAR_LAIR("clear lair",
            tile -> Optional.ofNullable(tile.getBuilding())
                    .map(Building::getIcon)
                    .filter(MONSTER_LAIR::equals)
                    .isPresent()),
    BUILD_VILLAGE("build village",
            tile -> Optional.ofNullable(tile)
                    .filter(t -> t.getBuilding() == null)
                    .map(Tile::getTerrain)
                    .map(Terrain::getIcon)
                    .filter(BARE_GROUND::equals)
                    .isPresent()),
    RECRUIT("recruit",
            tile -> Optional.ofNullable(tile)
                    .map(Tile::getBuilding)
                    .filter(building -> VILLAGE.equals(building.getIcon()))
                    .isPresent()),
    WAIT("wait",
            tile -> true),
    EMPTY("",
            tile -> true);
    public final String action;

    public final Predicate<Tile> actionAvailable;

    public static List<String> options(Tile tile) {
        return Arrays.stream(ConsoleActions.values())
                .filter(consoleActions -> consoleActions.actionAvailable.test(tile))
                .map(x -> x.action)
                .toList();
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public static ConsoleActions get(String value) {
        return Arrays.stream(ConsoleActions.values()).filter(x -> x.action.equals(value)).findFirst().get();
    }
}
