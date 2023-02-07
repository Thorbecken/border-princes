package eu.borderprinces.entities.building;

import eu.borderprinces.entities.unit.UnitLogic;
import eu.borderprinces.entities.unit.UnitType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RecruitmentOrder {

    private int priority;
    private long number;
    private UnitType unitType;
    private UnitLogic unitLogic;

}
