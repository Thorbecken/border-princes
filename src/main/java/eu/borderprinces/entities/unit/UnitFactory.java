package eu.borderprinces.entities.unit;

import eu.borderprinces.entities.Game;
import eu.borderprinces.entities.Tile;
import eu.borderprinces.entities.Unit;

public class UnitFactory {

    public static Unit createUnit(UnitType unitType, long teamId, Tile tile, String icon, Game game, UnitLogic unitLogic){
        Unit unit;
        switch (unitType){
            case SOLDIER -> unit = new Soldier(teamId, tile, icon, game, unitLogic);
            case FARMER -> unit = new Farmer(teamId, tile, icon, game, unitLogic);
            case BUILDER -> unit = new Builder(teamId, tile, icon, game, unitLogic);
            case MONSTER -> unit = new Monster(teamId, tile, icon, game, unitLogic);
            default -> throw new RuntimeException();
        }
        return unit;
    }
}
