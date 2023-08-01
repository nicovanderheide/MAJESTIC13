package data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
@AllArgsConstructor
public enum BaseUpgrade {
    Empty(""),
    Advanced_Cloning_Facility("When making the 2D6 roll for the outcome of the cloning process (see page 105), you may add or subtract 2 from the final result. If you may reroll on this table for any reason, you must choose a result from the reroll before applying this modifier."),
    Weapons_Depot("All team members gain a +1 bonus to damage with weapon attacks."),
    Combat_Training_Facility("The experience cost to increase the Combat stat of any team member is reduced by 1 (see Advancement, page 106)."),
    Advanced_Human_Mind_Data_Storage_Servers(""),
    Emergency_Medical_Facility(""),
    Upgraded_Point_Defense_System(""),
    Rapid_Deployment_Pad(""),
    REtech_Engineering_Lab(""),
    ;
    private final String benefits;

    public String getName() {
        if (this.equals(Empty)) {
            return "";
        }
        return name().replaceAll("_", " ");
    }
}
