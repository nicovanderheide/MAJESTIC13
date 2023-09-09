package data;

import data.enums.Abilities;
import data.enums.Faction;
import data.enums.Gear;
import data.enums.Weapon;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
public class Member implements Comparable<Member> {
    public static final int CMB_BOOST_3 = 24;
    private static final int HP_MODIFIER = 15;
    private static final int ACU_BOOST_1 = 14;
    private static final int ACU_BOOST_2 = 20;
    private static final int ACU_BOOST_3 = 25;
    private static final int CMB_BOOST_1 = 13;
    private static final int CMB_BOOST_2 = 18;
    private static final int DEX_BOOST_1 = 15;
    private static final int DEX_BOOST_2 = 21;
    private static final int DEX_BOOST_3 = 25;
    private static final int FORT_BOOST_1 = 14;
    private static final int FORT_BOOST_2 = 22;
    private static final int FORT_BOOST_3 = 25;

    private static final int PSI_START = 0;
    private static final int PSI_2ND = 10;
    private static final int PSI_3RD = 15;
    private static final int PSI_FINAL = 20;
    private static final int START_DEFENCE = 30;
    private static final int DEF_FORT_TRIGGER = 22;
    private Faction teamFaction;
    private String name;
    private int acuity;
    private int combat;
    private int dexterity;
    private int fortitude;
    private int psionics;
    private int exp;
    private List<String> injuries = new ArrayList<>();
    private Set<Abilities> abilities = new HashSet<>();
    private Set<Weapon> weapons = new HashSet<>();
    private Set<Gear> equipment = new HashSet<>();

    public int getDefense() {
        int defense = START_DEFENCE;
        if (null != equipment && equipment.contains(Gear.Shield)) {
            defense += 1;
        }
        if (getActualFortitude() >= DEF_FORT_TRIGGER) {
            defense += 1;
        }
        return defense;
    }

    public int getMaxHP() {
        return getActualFortitude() + HP_MODIFIER;
    }

    public int getActualAcuity() {
        return acuity + teamFaction.getAcuity();
    }

    public String getAcuityBonus() {
        final StringBuilder stringBuilder = new StringBuilder(1024);
        if (getActualAcuity() >= ACU_BOOST_1) {
            stringBuilder.append("No surprise 19-20; \n");
        }
        if (getActualAcuity() >= ACU_BOOST_2) {
            stringBuilder.append("If first Surprise attack; \n");
        }
        if (getActualAcuity() >= ACU_BOOST_3) {
            stringBuilder.append("Spot roll as free action; \n");
        }
        return stringBuilder.toString();
    }

    public int getActualCombat() {
        return combat + teamFaction.getCombat();
    }

    public String getCombatBonus() {
        final StringBuilder stringBuilder = new StringBuilder(1024);
        if (getActualCombat() >= CMB_BOOST_1) {
            stringBuilder.append("+1 dmg; \n");
        }
        if (getActualCombat() >= CMB_BOOST_2) {
            stringBuilder.append("19-20 crit; \n");
        }
        if (getActualCombat() >= CMB_BOOST_3) {
            stringBuilder.append("+1D6 dmg; \n");
        }
        return stringBuilder.toString();
    }

    public int getDamageBonus() {
        return getActualCombat() >= 14 ? 1 : 0;
    }

    public int getActualDexterity() {
        return dexterity + teamFaction.getDexterity();
    }

    public String getDexterityBonus() {
        final StringBuilder stringBuilder = new StringBuilder(1024);
        if (getActualDexterity() >= DEX_BOOST_1) {
            stringBuilder.append("ignore dangerous terrain; \n");
        }
        if (getActualDexterity() >= DEX_BOOST_2) {
            stringBuilder.append("jump -1 times distance; \n");
        }
        if (getActualDexterity() >= DEX_BOOST_3) {
            stringBuilder.append("Immune to Restrained; \n");
        }
        return stringBuilder.toString();
    }

    public int getActualFortitude() {
        return fortitude + teamFaction.getFortitude();
    }

    public String getFortitudeBonus() {
        final StringBuilder stringBuilder = new StringBuilder(1024);
        if (getActualFortitude() >= FORT_BOOST_1) {
            stringBuilder.append("-1 dmg reduction; \n");
        }
        if (getActualFortitude() >= FORT_BOOST_2) {
            stringBuilder.append("+1 defense; \n");
        }
        if (getActualFortitude() >= FORT_BOOST_3) {
            stringBuilder.append("Immune to Poison; \n");
        }
        return stringBuilder.toString();
    }

    public int getActualPsionics() {
        return psionics + teamFaction.getPsionics();
    }

    public String getPsionicsBonus() {
        final StringBuilder stringBuilder = new StringBuilder(1024);

        if (getActualPsionics() > PSI_START) {
            stringBuilder.append(getPsionicAbilities()).append(" abilities; \n");
        }
        if (getActualPsionics() >= PSI_FINAL) {
            stringBuilder.append("Remove Psionic condition; \n");
        }
        return stringBuilder.toString();
    }

    public int getPsionicAbilities() {
        int number = 0;
        if (getActualPsionics() > PSI_START) {
            number += 1;
        }
        if (getActualPsionics() >= PSI_2ND) {
            number += 2;
        }
        if (getActualPsionics() >= PSI_3RD) {
            number += 1;
        }
        if (getActualPsionics() >= PSI_FINAL) {
            number += 1;
        }
        return number;
    }

    @Override
    public int compareTo(final Member o) {
        int compared = 0;
        if (this.getActualAcuity() < o.getActualAcuity()) {
            compared = 1;
        } else if (this.getActualAcuity() > o.getActualAcuity()) {
            compared = -1;
        }
        return compared;
    }

    @Override
    public String toString() {
        return String.format("%s", getName());
    }
}
