package data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
@AllArgsConstructor
public enum Gear {
    Scanner("+2 Acuity vs Hidden"),
    Targeting_Assist("+2 combat"),
    Sighting_HUD("ignore cover"),
    Shield("defense +1")
    ;

    private final String output;

    public String toString() {
        return String.format(" - %s: %s", name().replaceAll("_", " "), getOutput());
    }
}
