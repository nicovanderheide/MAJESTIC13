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
    Advanced_Research_Station("Once after each mission, you may roll a D6. On a roll of 5+, your research facility has produced a breakthrough. Choose one ofthe items below and apply the benefit to your team going forward.You may never gain the same research breakthrough more than onceduring the course of a campaign (i.e. no single team may benefitfrom one of the items below more than once over their lifespan). Youmust complete at least one mission after receiving this base upgradebefore you can roll for the reward."),
    Drone_Control_Pod("Once per turn, when making an Acuity check to spot a Hidden enemy, any team member may utilize the drone instead of their normal vision and technology. If they do, they are treated as having an Acuity of 19 (if your team is Phase 2, this Acuity increases to 22) for that check. In addition, once per mission, as an action, a team member may have the drone strike at the enemy. They may do this, even if they do not have line of sight to the enemy, as long as the enemy is not Hidden. The strike is a Combat stat check (+19) and deals 5D6 damage on a successful attack."),
    Rapid_Deployment_Pad("During the first turn of the mission, your team gains a +5 bonus to initiative (i.e. each team member’s Acuity counts as 5 higher for determining the order of Activation during the first turn of the mission)."),
    Emergency_Medical_Facility("After each mission, when rolling on the Injury and Death table (see page 103) for your team members, you may choose to reroll the 3D6 roll once for any single team member. You must accept the second result."),
    Spy_Satellite_Link("When rolling on the Bureaucracy table for your Mission Determination in Step 4 (see page 70), you may reroll the dice after you know the results of your first roll. You must accept the second result if you choose to reroll."),
    Internet_Communication_Monitoring_Station("Once during each mission, your team may treat the FUBAR roll as though it was a 1 on the dice, regardless of the actual result."),
    Advanced_Human_Mind_Data_Storage_Servers("Team members restored through the cloning process (see page 104) do not lose any unspent experience. In addition, when rolling on the cloning table, a roll of 4 through 8 is considered a Success, instead of just 6-8."),
    Alien_Reclamation_and_Research_Facility("Whenever your team successfully completes a mission, each team member that participated in the mission gains 1 additional experience for each alien creature they killed during that mission (as it is returned to the base for additional autopsy and research). Members of FORCE and any aliens killed during Special Missions do not count for this benefit, only alien creatures killed during Standard Missions"),
    Upgraded_Point_Defense_System("When you are engaging in a Special Mission (see page 78), after all enemies are set up, roll a D6+3 for each enemy. Each enemy begins the mission having suffered an amount of damage equal to the roll on the D6+3."),
    REtech_Engineering_Lab("Your team may use one of its two equipment requisitions after each mission to request Phase 2 equipment, even if they are a Phase 1 team. When your team makes such a requisition, no other modifiers from Step 4: Bureaucracy apply except Veteran Team. When your team becomes a Phase 2 team and may requisition such gear normally, your team benefits from all normal bonuses in Step 4 and gains an additional +1 to the roll."),
    Human_Resources("When recruiting a new team member due to a failed Cloning attempt (see page 104), you may recruit that member with experience equal to your current team rating. If you do so, you may spend that experience as normal to upgrade that member when they are added to your roster. In addition, your team members have better access to support that gets them back in the field. If a team member is ever forced to skip a mission for any reason other than by choice (e.g., if they roll a 10 on the Injury and Death table), they gain 1 experience even though they did not participate in the mission. You do not gain this benefit if you chose NOT to bring a team member on a mission.");

    private final String benefits;

    public String getName() {
        return this.equals(Empty) ? "" : name().replaceAll("_", " ");
    }
}
