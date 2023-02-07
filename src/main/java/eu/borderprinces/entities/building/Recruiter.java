package eu.borderprinces.entities.building;

import eu.borderprinces.entities.*;
import eu.borderprinces.entities.unit.UnitFactory;
import lombok.NonNull;

import java.util.*;

public abstract class Recruiter extends Building {

    public Recruiter(long teamId, @NonNull Tile tile, @NonNull String icon, Color teamColor) {
        super(teamId, tile, icon, teamColor);
    }

    private final List<Unit> units = new ArrayList<>();
    private final HashMap<Integer, RecruitmentOrder> recruitmentOrders = new HashMap<>();

    public void recruit(Game game) {
        this.purgeTheDead();

        List<RecruitmentOrder> recipes = recruitmentOrders.values()
                .stream().sorted(Comparator.comparing(RecruitmentOrder::getPriority))
                .toList();

        for (RecruitmentOrder recruitmentOrder : recipes) {
            long count = units.stream()
                    .filter(unit -> unit.getUnitType() == recruitmentOrder.getUnitType())
                    .filter(unit -> unit.getUnitLogic() == recruitmentOrder.getUnitLogic())
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

    public void addOrder(RecruitmentOrder recruitmentOrder) {
        recruitmentOrders.put(recruitmentOrder.getPriority(), recruitmentOrder);
    }


    protected int getCurrentNumberOfUnits() {
        return units.size();
    }

    public Collection<RecruitmentOrder> currentOrders() {
        return recruitmentOrders.values();
    }
}
