package data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AdvancedResearchUpgrades {
    REtech_Weapons("Whenever your team makes an attack with a weapon, if they score a critical hit, they roll an additional 2D6 damage before the damage is doubled."),
    Psionic_Disruptions("Your team gains a +1 bonus to all stat checks made to avoid or clear a condition caused by an attack with the Psionic keyword."),
    Cloning_Advancement("Whenever a member of your team is cloned (see page 104), you may choose to increase one stat by up to 2 points. If you do so, you must then choose a different stat and decrease that stat by the same amount (i.e. by up to 2 points)."),
    Jump_Gear("When a member of your team is climbing (see page 21), they do not reduce their movement and each 1” of vertical movement counts as 1” of movement."),
    Ablative_Armor_Buffers("The first time in a mission any member of your team is damaged, reduce the damage that individual suffers to 0. This has no effect after the first time each team member has been damaged."),
    Echolocation_Sensors("Whenever a team member is Blinded (see Conditions, page 26), they retain line of sight to any enemies within 6”. They may make Acuity checks as normal to spot Hidden enemies within 6” of their position.");

    private final String description;
    public String getName() {
        return name().replaceAll("_", " ");
    }
}