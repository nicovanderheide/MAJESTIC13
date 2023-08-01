package roster;

import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import data.Member;
import data.Person;
import data.Team;
import data.enums.Abilities;
import data.enums.Equipment;
import lombok.extern.slf4j.Slf4j;
import parser.CrewReader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

@Slf4j
public class RosterBuilder {
    public void create(final Path crewFile) {

        String outputFile = crewFile.getFileName().toString()+".pdf";
        log.info("generating: {}", outputFile);
        try (final PdfDocument pdf = new PdfDocument(new PdfWriter(outputFile));
             final Document document = new Document(pdf, PageSize.A4).setFont(PdfFontFactory.createFont("Courier")).setFontSize(8)
        ) {
            Team team = CrewReader.read(crewFile);
            log.info("{}", team);

            document.add(createTeamTable(team));
            document.add(spacer(1));
            document.add(addPersonTable(team, team.getCommander()));

            document.add(spacer(1));
            if (team.getMembers() != null) {
                for (Member member : team.getMembers()) {
                    document.add(addPersonTable(team, member));
                    document.add(spacer(1));
                }
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Table createTeamTable(final Team team) {
        Table teamTable = new Table(2).setBorder(Border.NO_BORDER).useAllAvailableWidth();
        teamTable.addCell(createCell(String.format("TEAM NAME: %s", team.getName()),1));
        teamTable.addCell(createCell(String.format("FACTION: %s", team.getFaction().getName()),1));
        teamTable.startNewRow();
        teamTable.addCell(createCell(String.format("FACTION BONUS: %s", team.getFaction().getOutput()),2));
        teamTable.addCell(createCell(String.format("ADVANTAGE: %s", team.getAdvantage()),2));
        teamTable.addCell(createCell(String.format("BASE BONUS: %s", team.getBase().getOutput()),2));

        return teamTable;
    }

    private Table addPersonTable(final Team team, Person person) {
        Table table = new Table(UnitValue.createPointArray(new float[]{100,100,100,100,100})).setBorder(Border.NO_BORDER).useAllAvailableWidth();
        table.addCell(createCell("NAME: " + person.getName(), 2));
        table.addCell(createCell("DEFENSE: " + person.getDefense(), 2));
        table.addCell(createCell("MAX HP: " + person.getMaxHP() + "   CURRENT: ", 2));

        table.startNewRow();
        table.addCell(addAcuity(person));
        table.addCell(addCombat(person));
        table.addCell(addDexterity(person));
        table.addCell(addFortitude(person));
        table.addCell(addPsionics(person));
        table.startNewRow();

        if (!person.getEquipment().isEmpty()) {
            table.addCell(addEquipment(team, person));
        }
        if (person.getPsionics()>0 && !person.getAbilities().isEmpty()) {
            table.startNewRow();
            table.addCell(addAbilities(team, person));
        }

        return table;
    }

    private Cell addAcuity(Person person) {
        Cell cell = new Cell();
        cell.add(createCell("ACUITY: " + person.getAcuity()));
        cell.add(createCell(person.getAcuityBonus()));
        return cell;
    }

    private Cell addCombat(Person person) {
        Cell cell = new Cell();
        cell.add(createCell("COMBAT: " + person.getCombat()));
        cell.add(createCell(person.getCombatBonus()));
        return cell;
    }
    private Cell addDexterity(Person person) {
        Cell cell = new Cell();
        cell.add(createCell("DEXTERITY: " + person.getDexterity()));
        cell.add(createCell(person.getDexterityBonus()));
        return cell;
    }

    private Cell addFortitude(Person person) {
        Cell cell = new Cell();
        cell.add(createCell("FORTITUDE: " + person.getFortitude()));
        cell.add(createCell(person.getFortitudeBonus()));
        return cell;
    }
    private Cell addPsionics(Person person) {
        Cell cell = new Cell();
        cell.add(createCell("PSIONICS: " + person.getPsionics()));
        cell.add(createCell(person.getPsionicsBonus()));
        return cell;
    }

    private Cell addEquipment(Team team, Person person) {
        int dmgBonus = team.calculateDmg(person);
        Cell cell = new Cell(1,5);
        cell.add(new Paragraph("EQUIPMENT: "));

        person.getEquipment().stream().map(Equipment::find).forEach((equipment) -> cell.add(new Paragraph(equipment.toString().replace("{dmgBonus}", String.valueOf(dmgBonus)))));

        return cell;
    }

    private Cell addAbilities(Team team, Person person) {
        Cell cell = new Cell(1,5);
        cell.add(new Paragraph("ABILITIES: "));

        person.getAbilities().stream().map(Abilities::valueOf).forEach((ability) -> cell.add(new Paragraph(ability.toString())));

        return cell;
    }

    private Cell createCell(final String text, final int columns) {
        Cell cell = new Cell(1, columns);
        cell.add(new Paragraph(text));
        return cell;

    }

    private Cell createCell(final String text) {
        return createCell(text, 1);
    }


    private Cell spacer(int rows, int cols) {
        Cell cell = new Cell(rows, cols);
        cell.setBorder(Border.NO_BORDER);
        cell.add(new Div().setHeight(5));
        return cell;
    }

    private Cell spacer(int cols) {
        return spacer(1, cols);
    }
}
