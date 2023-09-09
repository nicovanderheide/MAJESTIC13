package data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

import static data.enums.BaseUpgrade.Advanced_Human_Mind_Data_Storage_Servers;
import static data.enums.BaseUpgrade.Emergency_Medical_Facility;
import static data.enums.BaseUpgrade.REtech_Engineering_Lab;
import static data.enums.BaseUpgrade.Rapid_Deployment_Pad;
import static data.enums.BaseUpgrade.Upgraded_Point_Defense_System;

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
        final StringBuilder stringBuilder = new StringBuilder();
        for (final BaseUpgrade upgrade : disallowedUpgrades) {
            stringBuilder.append(upgrade.getName());
            stringBuilder.append("; ");
        }
        return stringBuilder.toString();
    }
}
