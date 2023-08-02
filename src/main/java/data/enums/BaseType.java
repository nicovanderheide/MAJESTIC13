package data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

import static data.enums.BaseUpgrade.*;

@Getter
@AllArgsConstructor
public enum BaseType {
    Remote_Intelligence(
            "",
            8,
            List.of(REtech_Engineering_Lab)),
    Urban_Infiltration(
            "",
            6,
            List.of(Upgraded_Point_Defense_System, Rapid_Deployment_Pad)),
    Medical_Research(
            "",
            8,
            List.of(Emergency_Medical_Facility)),
    Military_Command(
            "Post game +1 Buro; +1 gear; Special mission 2D6 dmg to each enemy;",
            10,
            List.of(Advanced_Human_Mind_Data_Storage_Servers));
    private final String output;
    private final int maxUpgrades;
    private final List<BaseUpgrade> disallowedUpgrades;

    public String toString() {
        return name().replaceAll("_", " ");
    }

    public String getDisallowed() {
        StringBuilder sb = new StringBuilder();
        for (BaseUpgrade upgrade : disallowedUpgrades) {
            sb.append(upgrade.getName());
            sb.append("; ");
        }
        return sb.toString();
    }
}
