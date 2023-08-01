package data.enums;

public interface Equipment {
    static Equipment find(final String name) {
        Equipment found;
        try {
            found = Weapon.valueOf(name);
        } catch (IllegalArgumentException e) {
            found = Gear.valueOf(name);
        }
        return found;
    }
}
