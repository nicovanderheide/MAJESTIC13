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
import data.Person;
import data.Team;
import data.enums.Abilities;
import data.enums.BaseType;
import data.enums.BaseUpgrade;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import parser.YamlReader;

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
            Team team = YamlReader.read(crewFile);
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
        if (team.getMembers() != null) {
            team.getMembers().stream().sorted().forEach(member -> {
                document.add(addPersonTable(team, member));
                document.add(spacer(1));
            });

        }
    }

    private void addBaseDefinitionPage(Document document, Base base) {
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

    private Table addBaseUpgrade(Base base, BaseUpgrade upgrade) {
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

    private Table createBaseTypeTable(BaseType base) {
        Table table = new Table(2).setBorder(new SolidBorder(0.5f)).useAllAvailableWidth();
        table.addCell(createCell(String.format("STARTING BASE TYPE: %s", base)).setBorder(Border.NO_BORDER));
        table.addCell(createCell(String.format("MAX UPGRADES: %s", base.getMaxUpgrades())).setBorder(Border.NO_BORDER));
        table.startNewRow();
        table.addCell(createCell(String.format("DISALLOWED UPGRADES: %s", base.getDisallowed()), 2).setBorder(Border.NO_BORDER));
        table.startNewRow();
        table.addCell(createCell(String.format("BENEFITS: %s", base.getOutput()), 2).setBorder(Border.NO_BORDER));
        return table;
    }

    private Table createTeamTable(final Team team) {
        Table teamTable = new Table(3).setBorder(Border.NO_BORDER).useAllAvailableWidth();
        teamTable.addCell(createCell(String.format("TEAM NAME: %s", team.getName()), 1));
        teamTable.addCell(createCell(String.format("FACTION: %s", team.getFaction().getName()), 1));
        teamTable.addCell(createCell(String.format("TEAM RATING: %s", team.getRating()), 1));
        teamTable.startNewRow();
        teamTable.addCell(createCell(String.format("FACTION BONUS: %s", team.getFaction().getOutput()), 3));
        teamTable.addCell(createCell(String.format("ADVANTAGE: %s", team.getAdvantage()), 3));
        teamTable.addCell(createCell(String.format("BASE BONUS: %s", team.getBase().getBaseType().getOutput()), 3));

        return teamTable;
    }

    private Table addPersonTable(final Team team, Person person) {
        Table table = new Table(UnitValue.createPointArray(new float[]{100, 100, 100, 100, 100})).setBorder(Border.NO_BORDER).useAllAvailableWidth();
        String name = String.format("NAME: %s (%s)xp", person.getName(), person.getExp());
        if (name.contains("CMDR:")) {
            name = name.replace("NAME: ", "");
        }
        table.addCell(createCell(name, 2));
        table.addCell(createCell(String.format("DEFENSE: %s", person.getDefense()), 1));
        table.addCell(createCell(String.format("MAX HP: %s   CURRENT: ", person.getMaxHP()), 3));

        table.startNewRow();
        table.addCell(addAcuity(person));
        table.addCell(addCombat(person));
        table.addCell(addDexterity(person));
        table.addCell(addFortitude(person));
        table.addCell(addPsionics(person));
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

    private Cell addAcuity(Person person) {
        Cell cell = new Cell();
        cell.add(createCell(String.format("ACUITY: %s", person.getActualAcuity())));
        cell.add(createCell(person.getAcuityBonus()));
        return cell;
    }

    private Cell addCombat(Person person) {
        Cell cell = new Cell();
        cell.add(createCell(String.format("COMBAT: %s", person.getActualCombat())));
        cell.add(createCell(person.getCombatBonus()));
        return cell;
    }

    private Cell addDexterity(Person person) {
        Cell cell = new Cell();
        cell.add(createCell(String.format("DEXTERITY: %s", person.getActualDexterity())));
        cell.add(createCell(person.getDexterityBonus()));
        return cell;
    }

    private Cell addFortitude(Person person) {
        Cell cell = new Cell();
        cell.add(createCell(String.format("FORTITUDE: %s", person.getActualFortitude())));
        cell.add(createCell(person.getFortitudeBonus()));
        return cell;
    }

    private Cell addPsionics(Person person) {
        Cell cell = new Cell();
        cell.add(createCell(String.format("PSIONICS: %s", person.getActualPsionics())));
        cell.add(createCell(person.getPsionicsBonus()));
        return cell;
    }

    private Cell addEquipment(Team team, Person person) {
        int dmgBonus = team.calculateDmg(person);
        String extraDice = (person.getActualCombat() >= 24) ? "+1D6" : "";
        Cell cell = new Cell(1, 5);
        cell.add(new Paragraph("GEAR: "));
        if (person.getWeapons() != null) {
            person.getWeapons().forEach((weapon) -> cell.add(new Paragraph(StringUtils.replaceEach(weapon.toString(), new String[]{"{dmgBonus}", "{extra}"}, new String[]{String.valueOf(dmgBonus), extraDice}))));
        }
        if (person.getEquipment() != null) {
            person.getEquipment().forEach((equipment) -> cell.add(new Paragraph(equipment.toString())));
        }

        return cell;
    }

    private Cell addAbilities(Person person) {
        Cell cell = new Cell(1, 5);
        cell.add(new Paragraph("ABILITIES: "));
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

    private Cell addInjuries(final Person person) {
        Cell cell = new Cell(1, 5);
        cell.add(new Paragraph("INJURIES: "));
        person.getInjuries().forEach(injury -> cell.add(new Paragraph(String.format(" - %s", injury))));
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
