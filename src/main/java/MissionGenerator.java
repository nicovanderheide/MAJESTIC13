import data.enemies.EnemyTables;
import lombok.extern.slf4j.Slf4j;
import parser.YamlReader;
import roster.EnemiesBuilder;
import roster.RosterBuilder;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static java.nio.file.Files.walk;

@Slf4j
public class MissionGenerator {
    public static void main(String[] args) throws IOException {
        log.info("Mission generator");
        EnemyTables enemyTables = YamlReader.readEnemyTables();
        enemyTables.getEnemies().forEach(EnemiesBuilder::create);

        EnemiesBuilder.createMass(enemyTables);
    }
}
