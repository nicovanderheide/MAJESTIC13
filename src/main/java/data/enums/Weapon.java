package data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Weapon {
    // phase 1
    Pistol("1D6+{dmgBonus}{extra}"),
    Assault_Rifle("2D6+{dmgBonus}{extra}"),
    Sniper_Rifle("1D6+2+{dmgBonus} moving/ 2D6+4+{dmgBonus} stationary (ignore cover); {extra}"),
    Heavy_Repeating_Rifle("-2 Combat, 3D6+{dmgBonus}{extra}"),
    Flash_Bang_Grenade("+3” area; FORT 27 Stunned"),
    Explosives_Launcher("-4 Combat, 4D6+{dmgBonus}{extra}"),
    Restraining_Taser("1D6+{dmgBonus}{extra}; On hit DEX 27 or Restrained"),
    // phase 2
    REtech_Pistol("2D6+{dmgBonus}{extra}"),
    REtech_Assault_Rifle("3D6+{dmgBonus}{extra}"),
    REtech_Gauss_Rifle("2D6+{dmgBonus}{extra} (no DR)"),
    REtech_High_Energy_Rifle("+2 Combat, 2D6+{dmgBonus}{extra}"),

    ;

    private final String output;

    public String toString() {
        return String.format(" - %s: %s", name().replaceAll("_", " "), getOutput());
    }
}
