package data;

import lombok.Data;

import java.util.List;

@Data
public class Team {
    private String name;
    private String faction;
    private String factionBonus;
    private String advantage;
    private String base;

    private Commander commander;
    private List<Member> members;
}
