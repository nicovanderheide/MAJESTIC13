package data;

import data.enums.AdvancedResearchUpgrades;
import data.enums.BaseType;
import data.enums.BaseUpgrade;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Base {
    private BaseType baseType;
    private List<BaseUpgrade> baseUpgrades = new ArrayList<>();
    private List<AdvancedResearchUpgrades> advancedResearchUpgrades = new ArrayList<>();
}
