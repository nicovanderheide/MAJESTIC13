package data.enemies;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class Enemy {
    private String name;
    private Type type;
    private boolean flight;
    private String description;
    private int health;
    private int defense;
    private int rage;
    private int acuity;
    private int combat;
    private int dexterity;
    private int fortitude;
    private int psionics;

    private Set<Ability> abilities = new HashSet<>();
    private Set<Action> actions = new HashSet<>();

    public String getName() {
        return name.replaceAll("_", " ");
    }
}
