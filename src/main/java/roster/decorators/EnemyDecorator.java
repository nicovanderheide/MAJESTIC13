package roster.decorators;

import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import data.enemies.Ability;
import data.enemies.Action;
import data.enemies.Enemy;

public class EnemyDecorator extends Decorator {

    public static Table addEnemy(final Enemy enemy) {
        Table table = new Table(UnitValue.createPointArray(new float[]{250, 120, 100, 100, 100})).setBorder(Border.NO_BORDER).useAllAvailableWidth();
        table.addCell(createCell("NAME:", enemy.getName(),1));
        table.addCell(createCell("TYPE:", enemy.getType(),1));
        table.addCell(createCell("DEFENSE:" , enemy.getDefense(),1));
        table.addCell(createCell("MAX HP:", enemy.getHealth(),1));
        table.addCell(createCell("RAGE:", enemy.getRage(), 1));

        table.startNewRow();
        table.addCell(addActivations(enemy.getAcuity()));
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
    private static Cell addActivations(final int number) {
        Cell cell = new Cell();
        cell.add(new Paragraph().add(new Paragraph("ACU:").setBold()).add(String.format("%s | %s", number, number-10)));
        return cell;
    }

    private static Cell addStat(final String stat, final int number) {
        Cell cell = new Cell();
        cell.add(new Paragraph().add(new Paragraph(stat.concat(":")).setBold()).add(String.valueOf(number)));
        return cell;
    }

    private static Cell addAbilities(final Enemy enemy) {
        Cell cell = new Cell(1, 5);
        cell.add(new Paragraph("ABILITIES: ").setBold());
        cell.add(spacer(1));
        for (Ability ability : enemy.getAbilities()) {
            cell.add(new Paragraph(ability.getName().concat(":")).setBold());
            cell.add(new Paragraph(ability.getDescription()));
            cell.add(spacer(1));
        }
        return cell;
    }

    private static Cell addActions(final Enemy enemy) {
        Cell cell = new Cell(1, 5);
        cell.add(new Paragraph("ACTIONS: ").setBold());
        cell.add(spacer(1));
        for (Action action : enemy.getActions()) {
            cell.add(new Paragraph(action.getName().concat(":")).setBold());
            cell.add(new Paragraph(action.getDescription()));
            cell.add(spacer(1));
        }
        return cell;
    }
}
