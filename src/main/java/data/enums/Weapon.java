package data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Weapon {
    Pistol("1D6+{dmgBonus}{extra}"),
    Assault_Rifle("2D6+{dmgBonus}{extra}"),
    Sniper_Rifle("1D6+2+{dmgBonus} moving/ 2D6+4+{dmgBonus} stationary; {extra}"),
    Heavy_Repeating_Rifle("-2 Combat, 3D6+{dmgBonus}{extra}"),
    Flash_Bang_Grenade("+3\" area; FORT 27 Stunned"),
    Restraining_Taser("1D6+{dmgBonus}{extra}; On hit DEX 27 or Restrained")
    ;

    private final String output;

    public String toString() {
        return String.format(" - %s: %s", name().replaceAll("_", " "), getOutput());
    }
}
