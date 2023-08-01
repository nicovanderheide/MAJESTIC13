package data;

import data.enums.BaseType;
import data.enums.BaseUpgrade;
import lombok.Data;
import java.util.List;

@Data
public class Base {
    private BaseType baseType;
    private List<BaseUpgrade> baseUpgrades;
}
