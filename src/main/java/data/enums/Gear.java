package data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
@AllArgsConstructor
public enum Gear implements Equipment {
    scanner("+2 Acuity vs Hidden"),
    targeting_assist("+2 combat"),
    sighting_HUD("ignore cover"),
    shield("defense +1")
    ;

    private final String output;

    public String toString() {
        return String.format(" - %s: %s", StringUtils.capitalize(name().replaceAll("_", " ")), getOutput());
    }
}
