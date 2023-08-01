package data;

import data.enums.Base;
import data.enums.Faction;
import lombok.Data;

import java.util.List;

@Data
public class Team {
    private String name;
    private Faction faction;
    private String advantage;
    private Base base;

    private Commander commander;
    private List<Member> members;

    public int calculateDmg(Person member) {
        int dmg= faction.getDamageBonus();
        dmg += member.getDamageBonus();
        return dmg;
    }
}
