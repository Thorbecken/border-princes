package eu.borderprinces.entities;

import eu.borderprinces.ConsoleActions;

import java.util.EnumSet;

public class Player extends Unit {

    public Player(Tile tile, String icon, Game game) {
        super(tile, icon);
        game.player = this;
    }

    public EnumSet<ConsoleActions> getActions(){
        return EnumSet.of(ConsoleActions.QUIT,
                ConsoleActions.NEW_GAME,
                ConsoleActions.UP,
                ConsoleActions.DOWN,
                ConsoleActions.RIGHT,
                ConsoleActions.LEFT,
                ConsoleActions.CLEAR_LAIR);
    }


    public boolean isAvailableAction(String value){
        return getActions().stream().map(ConsoleActions::getAction).anyMatch(x -> x.equals(value));
    }
}
