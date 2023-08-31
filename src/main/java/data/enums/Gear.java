package data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Gear {
    Scanner("+2 Acuity vs Hidden"),
    Targeting_Assist("+2 combat"),
    Sighting_HUD("ignore cover"),
    Shield("defense +1"),
    Advances_Body_Armor("-1 dmg"),
    Medical_Kit("heal 1D6+4 1”"),

    ;

    private final String output;

    public String toString() {
        return String.format(" - %s: %s", name().replaceAll("_", " "), getOutput());
    }
}
