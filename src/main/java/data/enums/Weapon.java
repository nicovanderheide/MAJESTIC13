package data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
@AllArgsConstructor
public enum Weapon implements Equipment {
    pistol("1D6+{dmgBonus}"),
    assault_rifle("2D6+{dmgBonus}"),
    sniper_rifle("1D6+2+{dmgBonus} moving/ 2D6+4+{dmgBonus} stationary"),
    heavy_repeating_rifle("-2 Combat, 3D6+{dmgBonus}"),
    ;

    private final String output;

    public String toString() {
        return String.format(" - %s: %s", StringUtils.capitalize(name().replaceAll("_", " ")), getOutput());
    }
}
