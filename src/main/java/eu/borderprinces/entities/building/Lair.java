package eu.borderprinces.entities.building;

import eu.borderprinces.entities.Color;
import eu.borderprinces.entities.Game;
import eu.borderprinces.entities.Tile;
import eu.borderprinces.entities.unit.UnitType;
import lombok.NonNull;

import java.util.Random;

import static eu.borderprinces.entities.unit.UnitLogic.*;


public class Lair extends Recruiter {

    public Lair(long teamId, @NonNull Tile tile, @NonNull String icon, @NonNull UnitType monsterType) {
        super(teamId, tile, icon, Color.RED);
        this.addOrder(new RecruitmentOrder(1, 1, monsterType, DEFEND));
        this.addOrder(new RecruitmentOrder(2, 1, monsterType, PATROL));
        this.addOrder(new RecruitmentOrder(3, 99, monsterType, SEARCH_AND_DESTROY));
    }

    @Override
    public void takeTurn(Game game) {
        if (this.getTile().openOrFriendly(this.getTeamId())) {
            Random random = new Random();
            int change = random.nextInt(10);
            if (change == 0) {
                recruit(game);
            }
        }
    }
}
