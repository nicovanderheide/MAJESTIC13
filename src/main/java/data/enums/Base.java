package data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
@AllArgsConstructor
public enum Base {
    remote_intelligence("",8),
    urban_infiltration("",6),
    medical_research("",8),
    military_command("Post game +1 Buro; +1 gear; Special mission 2D6 dmg to each enemy;",10),

    ;
    private final String output;
    private final int maxUpgrades;

    public String toString() {
        return StringUtils.capitalize(name().replaceAll("_", " "));
    }
}
