package data;

import data.enums.Abilities;
import data.enums.Gear;
import data.enums.Weapon;
import lombok.Data;

import java.util.Set;

@Data
public class Person {
    private String name;
    private int acuity;
    private int combat;
    private int dexterity;
    private int fortitude;
    private int psionics;

    private Set<Abilities> abilities;
    private Set<Weapon> weapons;
    private Set<Gear> equipment;

    public int getDefense() {
        if (null != equipment && equipment.contains(Gear.Shield)) {
            return 31;
        }
        return 30;
    }

    public int getMaxHP() {
        return getFortitude() + 15;
    }

    public String getAcuityBonus() {
        StringBuilder sb = new StringBuilder();
        if (acuity >= 14) {
            sb.append("No surprise 19-20; ");
        }
        return sb.toString();
    }

    public String getCombatBonus() {
        StringBuilder sb = new StringBuilder();
        if (combat >= 13) {
            sb.append("+1 dmg; ");
        }
        return sb.toString();
    }
    public int getDamageBonus() {
        return getCombat() >= 14 ? 1 : 0;
    }

    public String getDexterityBonus() {
        StringBuilder sb = new StringBuilder();
        if (dexterity >= 15) {
            sb.append("ignore dangerous terrain; ");
        }
        return sb.toString();
    }

    public String getFortitudeBonus() {
        StringBuilder sb = new StringBuilder();
        if (fortitude >= 14) {
            sb.append("-1 dmg reduction; ");
        }
        return sb.toString();
    }

    public String getPsionicsBonus() {
        StringBuilder sb = new StringBuilder();

        if (psionics > 0) {
            sb.append("1 ability; ");
        }
        if (psionics >= 10) {
            sb.append("+2; ");
        }
        if (psionics >= 15) {
            sb.append("+1; ");
        }
        return sb.toString();
    }
}
