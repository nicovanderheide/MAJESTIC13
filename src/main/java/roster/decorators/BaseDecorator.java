package roster.decorators;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Table;
import data.Base;
import data.enums.BaseType;
import data.enums.BaseUpgrade;

public class BaseDecorator extends Decorator {

    public static void addBaseDefinitionPage(Document document, Base base) {
        document.add(createBaseTypeTable(base.getBaseType()));

        int upgrades = 1;
        for (BaseUpgrade upgrade : base.getBaseUpgrades()) {
            document.add(spacer(1));
            if (upgrades <= base.getBaseType().getMaxUpgrades()) {
                if (base.getBaseType().getDisallowedUpgrades().contains(upgrade)) {
                    document.add(addBaseUpgrade(base, upgrade).setFontColor(ColorConstants.RED));
                } else {
                    ++upgrades;
                    document.add(addBaseUpgrade(base, upgrade));
                }
            } else {
                document.add(addBaseUpgrade(base, upgrade).setFontColor(ColorConstants.BLUE));
            }
        }
    }

    private static Table addBaseUpgrade(Base base, BaseUpgrade upgrade) {
        Table table = new Table(1).setBorder(new SolidBorder(0.5f)).useAllAvailableWidth().setKeepTogether(true);
        table.addCell(createCell(upgrade.getName()).setBold().setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)));
        table.addCell(createCell(upgrade.getBenefits()).setBorder(Border.NO_BORDER));
        if (upgrade.equals(BaseUpgrade.Advanced_Research_Station)) {
            table.addCell(createCell("UNLOCKED UPGRADES:").setBold().setBorder(Border.NO_BORDER));
            base.getAdvancedResearchUpgrades().forEach(advancedResearchUpgrade -> {
                table.addCell(createCell(advancedResearchUpgrade.getName()).setBold().setBorder(Border.NO_BORDER));
                table.addCell(createCell(advancedResearchUpgrade.getDescription()).setBorder(Border.NO_BORDER));
            });
        }
        return table;
    }

    private static Table createBaseTypeTable(BaseType base) {

        Table table = new Table(2).useAllAvailableWidth().setBorder(new SolidBorder(0.5f));
        table.addCell(createCell("STARTING BASE TYPE:", base,1).setBorder(Border.NO_BORDER));
        table.addCell(createCell("MAX UPGRADES:", base.getMaxUpgrades(),1).setBorder(Border.NO_BORDER));

        table.addCell(createCell("DISALLOWED UPGRADES:", base.getDisallowed(),2).setBorder(Border.NO_BORDER));

        table.addCell(createCell("BENEFITS:", base.getOutput(), 2).setBorder(Border.NO_BORDER));
        return table;
    }
}
