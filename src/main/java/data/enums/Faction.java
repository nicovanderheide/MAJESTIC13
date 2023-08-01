package data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
@AllArgsConstructor
public enum Faction {
    Industrial_Arms("We make the weapons: +1 dmg; Upgrade: Ballistic/Energy", 0, 0, 0, 0, 0, 1);

    private final String output;
    private final int acuity;
    private final int combat;
    private final int dexterity;
    private final int fortitude;
    private final int psionics;

    private final int damageBonus;

    public String getName() {
        return name().replaceAll("_", " ");
    }
}
