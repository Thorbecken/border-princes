package eu.borderprinces.entities.unit;

import eu.borderprinces.entities.Game;
import eu.borderprinces.entities.Tile;
import eu.borderprinces.entities.Unit;

public class Player extends Unit {

    public Player(long teamId, Tile tile, String icon, Game game) {
        super(teamId, tile, icon, 10, game, UnitLogic.DEFEND);
        game.units.add(this);
        game.player = this;
    }

    @Override
    public void takeAction() {
    }
}
