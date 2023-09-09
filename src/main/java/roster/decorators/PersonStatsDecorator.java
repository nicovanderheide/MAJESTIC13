package roster.decorators;

import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import data.Member;

public class PersonStatsDecorator extends Decorator {

    public static Cell addAcuity(final Member member) {
        final Cell cell = new Cell();
        cell.add(new Paragraph().add(new Paragraph("ACUITY:").setBold()).add(String.valueOf(member.getActualAcuity())));
        cell.add(createCell(member.getAcuityBonus()));
        return cell;
    }

    public static Cell addCombat(final Member member) {
        final Cell cell = new Cell();
        cell.add(new Paragraph().add(new Paragraph("COMBAT:").setBold()).add(String.valueOf(member.getActualCombat())));
        cell.add(createCell(member.getCombatBonus()));
        return cell;
    }

    public static Cell addDexterity(final Member member) {
        final Cell cell = new Cell();
        cell.add(new Paragraph().add(new Paragraph("DEXTERITY:").setBold()).add(String.valueOf(member.getActualDexterity())));
        cell.add(createCell(member.getDexterityBonus()));
        return cell;
    }

    public static Cell addFortitude(final Member member) {
        final Cell cell = new Cell();
        cell.add(new Paragraph().add(new Paragraph("FORTITUDE:").setBold()).add(String.valueOf(member.getActualFortitude())));
        cell.add(createCell(member.getFortitudeBonus()));
        return cell;
    }

    public static Cell addPsionics(final Member member) {
        final Cell cell = new Cell();
        cell.add(new Paragraph().add(new Paragraph("PSIONICS:").setBold()).add(String.valueOf(member.getActualPsionics())));
        cell.add(createCell(member.getPsionicsBonus()));
        return cell;
    }
}
