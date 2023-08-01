package data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
@AllArgsConstructor
public enum Abilities {
    Invisibility("target+self become hidden"),
    Healing("heal 2D6+2"),
    Thrust("1D6 dmg, 6\" push"),
    ;

    private final String output;
    public String toString() {
        return String.format(" - %s: %s", name().replaceAll("_", " "), getOutput());
    }
}
