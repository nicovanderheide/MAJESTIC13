package data.enemies;

import lombok.Data;

import java.util.Set;

@Data
public class Table {
    private int min;
    private int max;
    private Set<String> outcome;
}
