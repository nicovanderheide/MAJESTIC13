package data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
@AllArgsConstructor
public enum Abilities {
    invisibility("target+self become hidden"),
    healing("heal 2D6+2"),
    thrust("1D6 dmg, 6\" push"),
    ;

    private final String output;
    public String toString() {
        return String.format(" - %s: %s", StringUtils.capitalize(name().replaceAll("_", " ")), getOutput());
    }
}
