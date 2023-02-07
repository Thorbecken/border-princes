package eu.borderprinces.entities.building;

import eu.borderprinces.entities.*;
import eu.borderprinces.entities.unit.UnitFactory;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public abstract class Recruiter extends Building {

    public Recruiter(long teamId, @NonNull Tile tile, @NonNull String icon, Color teamColor) {
        super(teamId, tile, icon, teamColor);
    }

    private final List<Unit> units = new ArrayList<>();
    private final HashMap<Integer, RecruitmentOrder> recruitmentRecipes = new HashMap<>();

    public void recruit(Game game) {
        this.purgeTheDead();

        List<RecruitmentOrder> recipes = recruitmentRecipes.values()
                .stream().sorted(Comparator.comparing(RecruitmentOrder::getPriority))
                .toList();

        for (RecruitmentOrder recruitmentOrder : recipes) {
            long count = units.stream()
                    .filter(unit -> unit.getUnitType() == recruitmentOrder.getUnitType())
                    .count();
            if (count < recruitmentOrder.getNumber()) {
                units.add(UnitFactory.createUnit(
                        recruitmentOrder.getUnitType()
                        , this.getTeamId()
                        , this.getTile()
                        , recruitmentOrder.getUnitType().getIcon()
                        , game
                        , recruitmentOrder.getUnitLogic()));
                return;
            }
        }
    }

    public void purgeTheDead() {
        List<Unit> deadUnits = units.stream()
                .filter(unit -> unit.getHealth() < 1)
                .toList();
        deadUnits.forEach(units::remove);
    }

    public void addRecipe(RecruitmentOrder recruitmentOrder) {
        recruitmentRecipes.put(recruitmentOrder.getPriority(), recruitmentOrder);
    }


    protected int getCurrentNumberOfUnits() {
        return units.size();
    }
}
