package data;

import data.enums.Abilities;
import data.enums.Faction;
import data.enums.Gear;
import data.enums.Weapon;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;


@Data
public class Person {
    private Faction teamFaction;

    private String name;
    private int acuity;
    private int combat;
    private int dexterity;
    private int fortitude;
    private int psionics;
    private int exp = 0;

    private Set<Abilities> abilities = new HashSet<>();
    private Set<Weapon> weapons = new HashSet<>();
    private Set<Gear> equipment = new HashSet<>();

    public int getDefense() {
        int defense = 30;
        if (null != equipment && equipment.contains(Gear.Shield)) {
            defense += 1;
        }
        if (getActualFortitude() >= 22) {
            defense += 1;
        }
        return defense;
    }

    public int getMaxHP() {
        return getActualFortitude() + 15;
    }

    public int getActualAcuity() {
        return acuity + teamFaction.getAcuity();
    }

    public String getAcuityBonus() {
        StringBuilder sb = new StringBuilder();
        if (getActualAcuity() >= 14) {
            sb.append("No surprise 19-20; \n");
        }
        if (getActualAcuity() >= 20) {
            sb.append("If first Surprise attack; \n");
        }
        if (getActualAcuity() >= 25) {
            sb.append("Spot roll as free action; \n");
        }
        return sb.toString();
    }

    public int getActualCombat() {
        return combat + teamFaction.getCombat();
    }

    public String getCombatBonus() {
        StringBuilder sb = new StringBuilder();
        if (getActualCombat() >= 13) {
            sb.append("+1 dmg; \n");
        }
        if (getActualCombat() >= 18) {
            sb.append("19-20 crit; \n");
        }
        if (getActualCombat() >= 24) {
            sb.append("+1D6 dmg; \n");
        }
        return sb.toString();
    }

    public int getDamageBonus() {
        return getActualCombat() >= 14 ? 1 : 0;
    }

    public int getActualDexterity() {
        return dexterity + teamFaction.getDexterity();
    }

    public String getDexterityBonus() {
        StringBuilder sb = new StringBuilder();
        if (getActualDexterity() >= 15) {
            sb.append("ignore dangerous terrain; \n");
        }
        if (getActualDexterity() >= 21) {
            sb.append("jump -1 times distance; \n");
        }
        if (getActualDexterity() >= 25) {
            sb.append("Immune to Restrained; \n");
        }
        return sb.toString();
    }

    public int getActualFortitude() {
        return fortitude + teamFaction.getFortitude();
    }

    public String getFortitudeBonus() {
        StringBuilder sb = new StringBuilder();
        if (getActualFortitude() >= 14) {
            sb.append("-1 dmg reduction; \n");
        }
        if (getActualFortitude() >= 22) {
            sb.append("+1 defense; \n");
        }
        if (getActualFortitude() >= 25) {
            sb.append("Immune to Poison; \n");
        }
        return sb.toString();
    }

    public int getActualPsionics() {
        return psionics + teamFaction.getPsionics();
    }

    public String getPsionicsBonus() {
        StringBuilder sb = new StringBuilder();

        if (getActualPsionics() > 0) {
            sb.append(getPsionicAbilities()).append(" abilities; \n");
        }
        if (getActualPsionics() >= 20) {
            sb.append("Remove Psionic condition; \n");
        }
        return sb.toString();
    }

    public int getPsionicAbilities() {
        int number = 0;
        if (getActualPsionics() > 0) {
            number += 1;
        }
        if (getActualPsionics() >= 10) {
            number += 2;
        }
        if (getActualPsionics() >= 15) {
            number += 1;
        }
        if (getActualPsionics() >= 20) {
            number += 1;
        }
        return number;
    }
}
