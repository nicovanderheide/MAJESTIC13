package roster.decorators;

import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import data.enemies.Ability;
import data.enemies.Action;
import data.enemies.Enemy;
import data.enemies.Type;

public class EnemyDecorator extends Decorator {

    public static Table addEnemy(final Enemy enemy) {
        final Table table = new Table(UnitValue.createPointArray(new float[]{250, 120, 100, 100, 100}))
                .setBorder(Border.NO_BORDER)
                .useAllAvailableWidth();
        table.addCell(createCell("NAME:", enemy.getName(), 1));
        table.addCell(createCell("TYPE:", enemy.getType(), 1));
        table.addCell(createCell("DEFENSE:", enemy.getDefense(), 1));
        table.addCell(createCell("MAX HP:", enemy.getHealth(), 1));
        table.addCell(createCell("RAGE:", enemy.getRage(), 1));

        table.startNewRow();
        table.addCell(createCell(enemy.getDescription(), 5));
        table.startNewRow();
        table.addCell(addActivations(enemy.getAcuity(), enemy.getType()));
        table.addCell(addStat("COM", enemy.getCombat()));
        table.addCell(addStat("DEX", enemy.getDexterity()));
        table.addCell(addStat("FOR", enemy.getFortitude()));
        table.addCell(addStat("PSI", enemy.getPsionics()));

        table.addCell(spacer(5));
        table.addCell(addAbilities(enemy));
        table.addCell(spacer(5));
        table.addCell(addActions(enemy));

        return table;
    }

    private static Cell addActivations(final int number, final Type type) {
        final Cell cell = new Cell();
        cell.add(new Paragraph().add(new Paragraph("ACU:").setBold()).add(String.format("   %s | %s | %s", number, number - 10, type.getActivation())));
        return cell;
    }

    private static Cell addStat(final String stat, final int number) {
        final Cell cell = new Cell();
        cell.add(new Paragraph().add(new Paragraph(stat.concat(":")).setBold()).add(String.valueOf(number)));
        return cell;
    }

    private static Cell addAbilities(final Enemy enemy) {
        final Cell cell = new Cell(1, 5);
        cell.add(new Paragraph("ABILITIES: ").setBold());
        cell.add(spacer(1));
        for (final Ability ability : enemy.getAbilities()) {
            cell.add(createParagraph(ability.getName().concat(":")).setBold());
            cell.add(createParagraph(ability.getDescription()));
        }
        return cell;
    }

    private static Cell addActions(final Enemy enemy) {
        final Cell cell = new Cell(1, 5);
        cell.add(new Paragraph("ACTIONS: ").setBold());
        cell.add(spacer(1));
        for (final Action action : enemy.getActions()) {
            cell.add(createParagraph(action.getName().concat(":")).setBold());
            cell.add(createParagraph(action.getDescription()));
        }
        return cell;
    }
}
