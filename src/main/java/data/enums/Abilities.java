package data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Abilities {
    Buffer("4+self +3 bonus to Psionic condition checks"),
    Thrust("1D6 dmg, 6” push"),
    Sight("target+self immune to blind"),
    Healing("heal 2D6+2"),
    Hold("1D6+3 dmg; Fort fail Restrained"),
    Spike("3D6 dmg, ignore cover"),
    Lift("target+self Fly"),
    Invisibility("target+self become Hidden"),
    Speed("target+self +2 Dexterity and Defense");

    private final String description;

    public String toString() {
        return String.format(" - %s: %s", name().replaceAll("_", " "), getDescription());
    }
}
