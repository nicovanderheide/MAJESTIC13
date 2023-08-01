package roster;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.UnitValue;
import data.Base;
import data.Member;
import data.Person;
import data.Team;
import data.enums.BaseType;
import data.enums.BaseUpgrade;
import lombok.extern.slf4j.Slf4j;
import parser.CrewReader;

import java.io.IOException;
import java.nio.file.Path;

@Slf4j
public class RosterBuilder {
    public void create(final Path crewFile) {

        String outputFile = crewFile.getFileName().toString().replace(".yml", ".pdf");
        log.info("Generating: {}", outputFile);
        try (final PdfDocument pdf = new PdfDocument(new PdfWriter(outputFile));
             final Document document = new Document(pdf, PageSize.A4).setFont(PdfFontFactory.createFont("Courier")).setFontSize(8)
        ) {
            Team team = CrewReader.read(crewFile);
            log.info("TEAM: {}", team);

            addTeamPage(document, team);
            document.add(new AreaBreak(PageSize.A4));
            addBaseDefinitionPage(document, team.getBase());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void addTeamPage(Document document, Team team) {
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
    }

    private void addBaseDefinitionPage(Document document, Base base) {
        document.add(createBaseTypeTable(base.getBaseType()));

        for (BaseUpgrade upgrade : base.getBaseUpgrades()) {
            document.add(spacer(1));
            if (!base.getBaseType().getDisallowedUpgrades().contains(upgrade)) {
                document.add(addBaseUpgrade(upgrade));
            } else {
                document.add(addBaseUpgrade(upgrade).setFontColor(ColorConstants.RED));
            }
        }
    }

    private Table addBaseUpgrade(BaseUpgrade upgrade) {
        Table table = new Table(1).setBorder(new SolidBorder(0.5f)).useAllAvailableWidth();
        table.addCell(createCell("UPGRADE: " + upgrade.getName()).setBorder(Border.NO_BORDER).setBorderBottom(new SolidBorder(0.5f)));
        table.addCell(createCell("BENEFITS: " + upgrade.getBenefits()).setHeight(UnitValue.createPointValue(30)).setBorder(Border.NO_BORDER));
        return table;
    }

    private Table createBaseTypeTable(BaseType base) {
        Table table = new Table(2).setBorder(new SolidBorder(0.5f)).useAllAvailableWidth();
        table.addCell(createCell("STARTING BASE TYPE: " + base.toString()).setBorder(Border.NO_BORDER));
        table.addCell(createCell("MAX UPGRADES: " + base.getMaxUpgrades()).setBorder(Border.NO_BORDER));
        table.startNewRow();
        table.addCell(createCell("DISALLOWED UPGRADES: " + base.getDisallowed(), 2).setBorder(Border.NO_BORDER));
        table.startNewRow();
        table.addCell(createCell("BENEFITS: " + base.getOutput(), 2).setBorder(Border.NO_BORDER));
        return table;
    }

    private Table createTeamTable(final Team team) {
        Table teamTable = new Table(2).setBorder(Border.NO_BORDER).useAllAvailableWidth();
        teamTable.addCell(createCell(String.format("TEAM NAME: %s", team.getName()), 1));
        teamTable.addCell(createCell(String.format("FACTION: %s", team.getFaction().getName()), 1));
        teamTable.startNewRow();
        teamTable.addCell(createCell(String.format("FACTION BONUS: %s", team.getFaction().getOutput()), 2));
        teamTable.addCell(createCell(String.format("ADVANTAGE: %s", team.getAdvantage()), 2));
        teamTable.addCell(createCell(String.format("BASE BONUS: %s", team.getBase().getBaseType().getOutput()), 2));

        return teamTable;
    }

    private Table addPersonTable(final Team team, Person person) {
        Table table = new Table(UnitValue.createPointArray(new float[]{100, 100, 100, 100, 100})).setBorder(Border.NO_BORDER).useAllAvailableWidth();
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

        table.addCell(addEquipment(team, person));

        if (person.getPsionics() != 0 && !person.getAbilities().isEmpty()) {
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
        Cell cell = new Cell(1, 5);
        cell.add(new Paragraph("GEAR: "));
        if (person.getWeapons() != null) {
            person.getWeapons().forEach((weapon) -> cell.add(new Paragraph(weapon.toString().replace("{dmgBonus}", String.valueOf(dmgBonus)))));
        }
        if (person.getEquipment() != null) {
            person.getEquipment().forEach((equipment) -> cell.add(new Paragraph(equipment.toString())));
        }

        return cell;
    }

    private Cell addAbilities(Team team, Person person) {
        Cell cell = new Cell(1, 5);
        cell.add(new Paragraph("ABILITIES: "));

        person.getAbilities().forEach((ability) -> cell.add(new Paragraph(ability.toString())));

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
