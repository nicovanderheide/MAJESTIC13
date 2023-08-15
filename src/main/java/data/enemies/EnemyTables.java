package data.enemies;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
public class EnemyTables {
    private Set<Table> tables;
    private Set<Enemy> enemies;
}
