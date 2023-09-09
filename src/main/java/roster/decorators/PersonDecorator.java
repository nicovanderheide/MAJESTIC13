package roster.decorators;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import data.Member;
import data.Team;
import data.enums.Abilities;
import org.apache.commons.lang3.StringUtils;

import static data.Member.CMB_BOOST_3;
import static roster.decorators.PersonStatsDecorator.addAcuity;
import static roster.decorators.PersonStatsDecorator.addCombat;
import static roster.decorators.PersonStatsDecorator.addDexterity;
import static roster.decorators.PersonStatsDecorator.addFortitude;
import static roster.decorators.PersonStatsDecorator.addPsionics;

public class PersonDecorator extends Decorator {

    public static Table addPersonTable(final Team team, final Member member) {
        final Table table = new Table(UnitValue.createPointArray(new float[]{100, 100, 100, 100, 100})).setBorder(Border.NO_BORDER).useAllAvailableWidth();
        addPersonTopRow(member, table);
        table.startNewRow();
        addStatsBlock(member, table);
        table.startNewRow();

        table.addCell(addEquipment(team, member));

        if (member.getActualPsionics() != 0 && !member.getAbilities().isEmpty()) {
            table.startNewRow();
            table.addCell(addAbilities(member));
        }

        if (!member.getInjuries().isEmpty()) {
            table.startNewRow();
            table.addCell(addInjuries(member));
        }

        return table;
    }

    private static void addPersonTopRow(final Member member, final Table table) {
        String name = String.format("%s (%s)xp", member.getName(), member.getExp());
        if (name.contains("CMDR:")) {
            name = name.replace("CMDR:", "");
            table.addCell(createCell("CMDR:", name, 2));
        } else {
            table.addCell(createCell("NAME:", name, 2));
        }
        table.addCell(createCell("DEFENSE:", member.getDefense(), 1));
        table.addCell(createCell("MAX HP:", member.getMaxHP(), 1));
        table.addCell(createCell("CURRENT:", "", 2));
    }

    private static void addStatsBlock(final Member member, final Table table) {
        table.addCell(addAcuity(member));
        table.addCell(addCombat(member));
        table.addCell(addDexterity(member));
        table.addCell(addFortitude(member));
        table.addCell(addPsionics(member));
    }

    private static Cell addEquipment(final Team team, final Member member) {
        final int dmgBonus = team.calculateDmg(member);
        final String extraDice = member.getActualCombat() >= CMB_BOOST_3 ? "+1D6" : "";
        final Cell cell = new Cell(1, 5);
        cell.add(new Paragraph("GEAR: ").setBold());
        if (member.getWeapons() != null) {
            member.getWeapons().forEach((weapon) -> cell.add(new Paragraph(StringUtils.replaceEach(weapon.toString(), new String[]{"{dmgBonus}", "{extra}"}, new String[]{String.valueOf(dmgBonus), extraDice}))));
        }
        if (member.getEquipment() != null) {
            member.getEquipment().forEach((equipment) -> cell.add(new Paragraph(equipment.toString())));
        }

        return cell;
    }

    private static Cell addAbilities(final Member member) {
        final Cell cell = new Cell(1, 5);
        cell.add(new Paragraph("ABILITIES: ").setBold());
        int abilities = 0;
        for (final Abilities ability : member.getAbilities()) {
            abilities -= -1;
            if (abilities > member.getPsionicAbilities()) {
                cell.add(createParagraph(ability.toString()).setFontColor(ColorConstants.BLUE));
            } else {
                cell.add(createParagraph(ability.toString()));
            }

        }

        return cell;
    }

    private static Cell addInjuries(final Member member) {
        final Cell cell = new Cell(1, 5);
        cell.add(new Paragraph("INJURIES: ").setBold());
        member.getInjuries().forEach(injury -> cell.add(new Paragraph(String.format(" - %s", injury))));
        return cell;
    }
}
