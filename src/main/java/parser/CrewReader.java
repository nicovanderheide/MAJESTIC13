package parser;

import data.Member;
import data.Team;
import data.enums.BaseUpgrade;
import data.enums.Faction;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class CrewReader {
    public static Team read(final Path file) throws IOException {
        try (InputStream inputStream = Files.newInputStream(file)) {
            Yaml yaml = new Yaml(new Constructor(Team.class));
            Team team = yaml.load(inputStream);
            Faction faction = team.getFaction();
            while (team.getBase().getBaseUpgrades().size() < team.getBase().getBaseType().getMaxUpgrades()) {
                team.getBase().getBaseUpgrades().add(BaseUpgrade.Empty);
            }
            team.getCommander().setTeamFaction(faction);
            for (Member member : team.getMembers()) {
                member.setTeamFaction(faction);
            }

            return team;
        }
    }
}
