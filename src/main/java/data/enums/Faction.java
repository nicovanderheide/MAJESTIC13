package data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Faction {
    ODEAR,
    Psight_Ops,
    Agricultural_League,
    Industrial_Arms("We make the weapons: +1 dmg; Upgrade: Ballistic/Energy", 0, 0, 0, 0, 0, 1),
    The_Sanctum,
    Holy_Ground_Base,
    Silicon_Syndicate,
    The_Naturalized,
    Womens_Defense_Force,
    Roughnecks("Ignore Hazards; -1 Bureaucracy; Sharp and Tough: +2 Acuity & Fortitude.", 2, 0, 0, 2, 0, 0),
    Section_Six,
    Hippocratic_Mercy,
    The_One_Percent,
    Dispersed,

    ;

    private final String output;
    private final int acuity;
    private final int combat;
    private final int dexterity;
    private final int fortitude;
    private final int psionics;

    private final int damageBonus;

    Faction() {
        output = "";
        acuity = 0;
        combat = 0;
        dexterity = 0;
        fortitude = 0;
        psionics = 0;
        damageBonus = 0;
    }

    public String getName() {
        return name().replaceAll("_", " ");
    }
}
