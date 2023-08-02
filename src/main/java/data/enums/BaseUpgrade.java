package data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BaseUpgrade {
    Empty(""),
    Advanced_Cloning_Facility("When making the 2D6 roll for the outcome of the cloning process (see page 105), you may add or subtract 2 from the final result. If you may reroll on this table for any reason, you must choose a result from the reroll before applying this modifier."),
    Weapons_Depot("All team members gain a +1 bonus to damage with weapon attacks."),
    Combat_Training_Facility("The experience cost to increase the Combat stat of any team member is reduced by 1 (see Advancement, page 106)."),
    Advanced_Research_Station(""),
    Drone_Control_Pod(""),
    Rapid_Deployment_Pad(""),
    Emergency_Medical_Facility(""),
    Spy_Satellite_Link(""),
    Internet_Communication_Monitoring_Station(""),
    Advanced_Human_Mind_Data_Storage_Servers(""),
    Alien_Reclamation_and_Research_Facility(""),
    Upgraded_Point_Defense_System(""),
    REtech_Engineering_Lab(""),
    Human_Resources("");

    private final String benefits;

    public String getName() {
        return this.equals(Empty) ? "" : name().replaceAll("_", " ");
    }
}
