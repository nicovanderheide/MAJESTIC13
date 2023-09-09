package data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Advantage {
    Advanced_Combat_Training,
    Armored_Team,
    Chemically_Enhanced,
    Connected,
    Environmental_Specialists,
    Extra_Gear,
    High_Alert_Team,
    Psionic_Talent,
    Rapid_Response_Team,
    Ready_to_Respond,
    Reinforced_Dermal_Layering,
    Roughest_and_Toughest,
    Tactical_Command_Team,
    Vertical_Response_Team,
    Veteran_Commander,
    Weapons_Specialists,
    Well_Armed,
    ;

    private final String output;

    Advantage() {
        output = "";
    }

    public String toString() {
        return name().replaceAll("_", " ");
    }
}
