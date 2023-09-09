package generators;

import data.enemies.Enemy;
import data.enemies.EnemyTables;
import lombok.extern.slf4j.Slf4j;
import parser.YamlReaderUtil;
import roster.EnemiesBuilder;

import java.io.IOException;

@Slf4j
public final class MissionGenerator {
    private MissionGenerator() {
    }

    public static void main(final String[] args) throws IOException {
        log.info("Mission generator");
        final EnemyTables enemyTables = YamlReaderUtil.readEnemyTables();
        for (final Enemy enemy : enemyTables.getEnemies()) {
            EnemiesBuilder.create(enemy);
        }
        EnemiesBuilder.createMass(enemyTables);
    }
}
