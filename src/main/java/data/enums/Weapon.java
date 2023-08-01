package data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
@AllArgsConstructor
public enum Weapon {
    Pistol("1D6+{dmgBonus}"),
    Assault_Rifle("2D6+{dmgBonus}"),
    Sniper_Rifle("1D6+2+{dmgBonus} moving/ 2D6+4+{dmgBonus} stationary"),
    Heavy_Repeating_Rifle("-2 Combat, 3D6+{dmgBonus}"),
    ;

    private final String output;

    public String toString() {
        return String.format(" - %s: %s", name().replaceAll("_", " "), getOutput());
    }
}
