package parser;

import data.Member;
import data.Team;
import data.enemies.EnemyTables;
import data.enums.BaseUpgrade;
import data.enums.Faction;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class YamlReader {
    public static Team read(final Path file) throws IOException {
        try (InputStream inputStream = Files.newInputStream(file)) {
            Yaml yaml = new Yaml(new Constructor(Team.class));
            Team team = yaml.load(inputStream);
            Faction faction = team.getFaction();
            while (team.getBase().getBaseUpgrades().size() < team.getBase().getBaseType().getMaxUpgrades()) {
                team.getBase().getBaseUpgrades().add(BaseUpgrade.Empty);
            }
            for (Member member : team.getMembers()) {
                member.setTeamFaction(faction);
            }

            return team;
        }
    }

    public static EnemyTables readEnemyTables()  throws IOException {
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("enemyTables.yaml")) {
            Yaml yaml = new Yaml(new Constructor(EnemyTables.class));
            EnemyTables enemyTables = yaml.load(inputStream);
            return enemyTables;
        }
    }
}
