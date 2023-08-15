package data;

import data.enums.Advantage;
import data.enums.BaseUpgrade;
import data.enums.Faction;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class Team {
    private String name;
    private int rating;
    private Faction faction;
    private Advantage advantage;
    private Base base;

    private Set<Member> members;

    public int calculateDmg(Person member) {
        int dmg = faction.getDamageBonus();
        if (base.getBaseUpgrades().contains(BaseUpgrade.Weapons_Depot)) {
            dmg += 1;
        }
        dmg += member.getDamageBonus();
        return dmg;
    }
}
