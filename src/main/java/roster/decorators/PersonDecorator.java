package roster.decorators;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import data.Person;
import data.Team;
import data.enums.Abilities;
import org.apache.commons.lang3.StringUtils;

public class PersonDecorator extends Decorator {

    public static Table addPersonTable(final Team team, Person person) {
        Table table = new Table(UnitValue.createPointArray(new float[]{100, 100, 100, 100, 100})).setBorder(Border.NO_BORDER).useAllAvailableWidth();
        addPersonTopRow(person, table);
        table.startNewRow();
        addStatsBlock(person, table);
        table.startNewRow();

        table.addCell(addEquipment(team, person));

        if (person.getActualPsionics() != 0 && !person.getAbilities().isEmpty()) {
            table.startNewRow();
            table.addCell(addAbilities(person));
        }

        if (!person.getInjuries().isEmpty()) {
            table.startNewRow();
            table.addCell(addInjuries(person));
        }

        return table;
    }

    private static void addPersonTopRow(Person person, Table table) {
        String name = String.format("%s (%s)xp", person.getName(), person.getExp());
        if (name.contains("CMDR:")) {
            name = name.replace("CMDR:", "");
            table.addCell(createCell("CMDR:", name, 2));
        } else {
            table.addCell(createCell("NAME:", name, 2));
        }
        table.addCell(createCell("DEFENSE:", person.getDefense(), 1));
        table.addCell(createCell("MAX HP:", person.getMaxHP(), 1));
        table.addCell(createCell("CURRENT:", "", 2));
    }

    private static void addStatsBlock(Person person, Table table) {
        table.addCell(addAcuity(person));
        table.addCell(addCombat(person));
        table.addCell(addDexterity(person));
        table.addCell(addFortitude(person));
        table.addCell(addPsionics(person));
    }

    private static Cell addAcuity(Person person) {
        Cell cell = new Cell();
        cell.add(new Paragraph().add(new Paragraph("ACUITY:").setBold()).add(String.valueOf(person.getActualAcuity())));
        cell.add(createCell(person.getAcuityBonus()));
        return cell;
    }

    private static Cell addCombat(Person person) {
        Cell cell = new Cell();
        cell.add(new Paragraph().add(new Paragraph("COMBAT:").setBold()).add(String.valueOf(person.getActualCombat())));
        cell.add(createCell(person.getCombatBonus()));
        return cell;
    }

    private static Cell addDexterity(Person person) {
        Cell cell = new Cell();
        cell.add(new Paragraph().add(new Paragraph("DEXTERITY:").setBold()).add(String.valueOf(person.getActualDexterity())));
        cell.add(createCell(person.getDexterityBonus()));
        return cell;
    }

    private static Cell addFortitude(Person person) {
        Cell cell = new Cell();
        cell.add(new Paragraph().add(new Paragraph("FORTITUDE:").setBold()).add(String.valueOf(person.getActualFortitude())));
        cell.add(createCell(person.getFortitudeBonus()));
        return cell;
    }

    private static Cell addPsionics(Person person) {
        Cell cell = new Cell();
        cell.add(new Paragraph().add(new Paragraph("PSIONICS:").setBold()).add(String.valueOf(person.getActualPsionics())));
        cell.add(createCell(person.getPsionicsBonus()));
        return cell;
    }

    private static Cell addEquipment(Team team, Person person) {
        int dmgBonus = team.calculateDmg(person);
        String extraDice = (person.getActualCombat() >= 24) ? "+1D6" : "";
        Cell cell = new Cell(1, 5);
        cell.add(new Paragraph("GEAR: ").setBold());
        if (person.getWeapons() != null) {
            person.getWeapons().forEach((weapon) -> cell.add(new Paragraph(StringUtils.replaceEach(weapon.toString(), new String[]{"{dmgBonus}", "{extra}"}, new String[]{String.valueOf(dmgBonus), extraDice}))));
        }
        if (person.getEquipment() != null) {
            person.getEquipment().forEach((equipment) -> cell.add(new Paragraph(equipment.toString())));
        }

        return cell;
    }

    private static Cell addAbilities(Person person) {
        Cell cell = new Cell(1, 5);
        cell.add(new Paragraph("ABILITIES: ").setBold());
        int abilities = 0;
        for (Abilities ability : person.getAbilities()) {
            if (++abilities > person.getPsionicAbilities()) {
                cell.add(new Paragraph(ability.toString()).setFontColor(ColorConstants.BLUE));
            } else {
                cell.add(new Paragraph(ability.toString()));
            }

        }

        return cell;
    }

    private static Cell addInjuries(final Person person) {
        Cell cell = new Cell(1, 5);
        cell.add(new Paragraph("INJURIES: ").setBold());
        person.getInjuries().forEach(injury -> cell.add(new Paragraph(String.format(" - %s", injury))));
        return cell;
    }
}
