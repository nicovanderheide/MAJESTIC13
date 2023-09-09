package data.enemies;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Type {
    Monstrosity("On Crit"),
    Ravager("On Miss"),
    Stalker("On Spot");

    private String activation;
}
