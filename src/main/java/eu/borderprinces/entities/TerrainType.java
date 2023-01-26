package eu.borderprinces.entities;

import eu.borderprinces.BorderPrincesConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TerrainType {

    PLAIN(1d, BorderPrincesConstants.PLAIN, Color.YELLOW),
    BRIDGE(1d, BorderPrincesConstants.BRIDGE, Color.YELLOW),
    BARE_GROUND(1, BorderPrincesConstants.BARE_GROUND, Color.YELLOW),
    FERTILE_GROUND(1, BorderPrincesConstants.FERTILE_GROUND, Color.YELLOW),
    WATER(1d, BorderPrincesConstants.WATER, Color.BLUE),
    FOREST(2d, BorderPrincesConstants.FOREST, Color.GREEN),
    MOUNTAIN(3d, BorderPrincesConstants.MOUNTAIN, Color.WHITE);

    private final double movementCost;
    private final String icon;
    private final Color color;

    static public TerrainType getTerrainType(String type){
        if(BorderPrincesConstants.PLAIN.equals(type)){
            return PLAIN;
        } else if (BorderPrincesConstants.BRIDGE.equals(type)){
            return BRIDGE;
        } else if (BorderPrincesConstants.BARE_GROUND.equals(type)){
            return BARE_GROUND;
        } else if (BorderPrincesConstants.WATER.equals(type)){
            return WATER;
        } else if (BorderPrincesConstants.FOREST.equals(type)){
            return FOREST;
        } else if (BorderPrincesConstants.MOUNTAIN.equals(type)){
            return MOUNTAIN;
        } else if (BorderPrincesConstants.FERTILE_GROUND.equals(type)){
            return FERTILE_GROUND;
        } else {
            throw new RuntimeException("Type " + type + " not implemented");
        }
    }

    public String getColored() {
        return this.color.getCode()+this.icon;
    }
}
