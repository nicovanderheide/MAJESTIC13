package roster;

import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import data.enemies.Enemy;
import data.enemies.EnemyTables;
import data.enemies.Type;
import lombok.extern.slf4j.Slf4j;
import roster.decorators.EnemyDecorator;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

@Slf4j
public final class EnemiesBuilder {

    private static final AreaBreak EMPTY = new AreaBreak();

    private EnemiesBuilder() {
    }

    public static void createMass(final EnemyTables enemyTables) throws IOException {
        try (PdfDocument pdf = new PdfDocument(new PdfWriter(new File("enemies", "_ALL_Phase_1.pdf")));
             Document document = new Document(pdf, PageSize.A4)
                     .setFont(PdfFontFactory.createFont("Courier"))
                     .setFontSize(7)
        ) {
            final Cell monstrosity = addEnemyType(Type.Monstrosity);
            final Cell ravager = addEnemyType(Type.Ravager);
            final Cell stalker = addEnemyType(Type.Stalker);

            final Iterator<Enemy> iterator = enemyTables.getEnemies().iterator();
            do {
                final Enemy enemy = iterator.next();
                document.add(EnemyDecorator.addEnemy(enemy));
                document.add(EnemyDecorator.spacer(1));
                switch (enemy.getType()) {
                    case Monstrosity -> document.add(monstrosity);
                    case Ravager -> document.add(ravager);
                    case Stalker -> document.add(stalker);
                    default -> document.add(createParagraph("default"));
                }
                if (iterator.hasNext()) {
                    document.add(EMPTY);
                }
            } while (iterator.hasNext());
        }
    }

    public static Paragraph createParagraph(final String text) {
        return new Paragraph(text);
    }

    public static void create(final Enemy enemy) throws IOException {
        if (!enemy.getActions().isEmpty()) {
            final String outputFile = enemy.getName().concat(".pdf");
            log.info("Generating: {}", outputFile);
            try (PdfDocument pdf = new PdfDocument(new PdfWriter(new File("enemies", outputFile)));
                 Document document = new Document(pdf, PageSize.A4).setFont(PdfFontFactory.createFont("Courier")).setFontSize(7)
            ) {
                document.add(EnemyDecorator.addEnemy(enemy));
                document.add(EnemyDecorator.spacer(1));
                document.add(addEnemyType(enemy.getType()));
            }
        }
    }


    private static Cell addEnemyType(final Type type) {
        final Cell cell = new Cell();
        cell.add(new Paragraph(type.name()).setBold());
        final Table table = new Table(2).setFontSize(7);
        switch (type) {
            case Monstrosity -> outputMonstrosity(cell, table);
            case Ravager -> outputRavager(cell, table);
            case Stalker -> outputStalker(cell, table);
            default -> outputDefault(cell, table);
        }
        cell.add(table);
        return cell;
    }

    private static void outputDefault(final Cell cell, final Table table) {
        cell.add(new Paragraph("Nothing"));
        table.addCell("Nothing");
    }

    private static void outputMonstrosity(final Cell cell, final Table table) {
        cell.add(new Paragraph("A Monstrosity first activates according to its Acuity. A Monstrosity " +
                "activates again at its Acuity -10. A Monstrosity will also activate again " +
                "if it suffers a critical hit. In this event, it will make a bonus activation " +
                "immediately after the conclusion of the activation of the model that " +
                "scored the critical hit. A Monstrosity may never activate more than 3 " +
                "times during any turn."));
        table.addCell("1.");
        table.addCell("If the Monstrosity starts its activation In Extremis, it will suffer 2D6 damage and take an extra action during its activation. It will use this action as per the order below.");
        table.addCell("2.");
        table.addCell("If the Monstrosity starts its activation within 1” of an enemy it did not attack during its last activation (including as part of an area attack that affected multiple enemies), it will attack that enemy. If multiple enemies that it did not attack are within 1”, it will attack the enemy with lowest Hit Points (if there are multiple enemies within that range tied for lowest Hit Points, randomly determine a target). If the Monstrosity uses an area attack, it must include this enemy in the area.");
        table.addCell("3.");
        table.addCell("If the Monstrosity starts its activation within 1” of an enemy it attacked during its last activation (including as part of an area attack), it will move up to its full movement distance toward the enemy with the lowest Hit Points within its range and make the first listed attack on its profile. If there are no other enemies within its movement distance, or if the enemy already within 1” is the enemy with the lowest Hit Points, it will continue to attack the enemy within 1”.");
        table.addCell("4.");
        table.addCell("If the Monstrosity starts its activation within its movement distance of an enemy and there is no enemy within 1”, it will move to the enemy with the lowest Hit Points within its range and attack.");
        table.addCell("5.");
        table.addCell("If the Monstrosity starts its activation and there are no enemies within its movement distance but there are enemies within line of sight, it will move and then move again to be within 1” of as many enemies as possible that it can see.");
        table.addCell("6.");
        table.addCell("If the Monstrosity starts its activation and there are no visible enemies (either because they are all Hidden or because they are all out of line of sight), it will attempt to spot the Hidden enemies (see Hidden, page 28) as an action, as opposed to as part of an attack. If successful, it will then move as close as possible to the now-visible enemy or enemies within its line of sight, seeking to end within 1” of as many enemies as possible. If only one is visible, it will move to within 1” of the now closest visible enemy. If it fails to spot any Hidden enemies, or all enemies are out of line of sight, it will move in a random direction up to its full movement distance. If it has additional actions (because it is In Extremis) and it has spotted an enemy, it will use these actions to make attacks against that enemy as per Step 2 above.");
    }

    private static void outputStalker(final Cell cell, final Table table) {
        cell.add(new Paragraph("A Stalker first activates according to its Acuity. " +
                "A Stalker activates again at its Acuity -10. " +
                "A Stalker will also activate again if it is Hidden and an enemy successfully reveals the Stalker from Hidden. " +
                "Once the team member that successfully spotted the Stalker has completed their turn, the Stalker will make a bonus activation. " +
                "A Stalker may never activate more than 3 times during any turn."));

        table.addCell("1.");
        table.addCell("If the Stalker starts its activation In Extremis, it will suffer 2D6 damage and take an extra action during its activation. It will use this action as per the order below.");
        table.addCell("2.");
        table.addCell("If the Stalker starts its activation within 1” of an enemy, it will attack that enemy (if there are multiple enemies within range, it will attack the one with the lowest Hit Points. If there are multiple enemies tied with the lowest Hit Points, it will choose its target randomly). The Stalker will then move up to its maximum movement distance away from that enemy, ending in cover if possible.");
        table.addCell("3.");
        table.addCell("If the Stalker starts its activation outside of 1” of all enemies and is not Hidden, it will move to cover against the nearest enemy if possible. If it has an action on its profile to become Hidden, it will then use that action and become Hidden. If it does not have this action available, it will move out of line of sight to as many enemies as possible, ending in cover if possible.");
        table.addCell("4.");
        table.addCell("If the Stalker starts its activation outside of 1” of all enemies and it is Hidden, it will move to the closest enemy it can see and make an attack with Surprise.");
        table.addCell("5.");
        table.addCell("If the Stalker starts its activation outside of 1” of all enemies and it is Hidden and there is no enemy within its movement, it will move its full movement – moving twice if necessary – toward the enemy with the lowest Hit Points, ending at least 1” away and remaining Hidden.");
        table.addCell("6.");
        table.addCell("If the Stalker starts its activation outside of 1” of all enemies while it is Hidden and all enemies are also Hidden, it will make an Acuity check to spot an enemy as an action. If it successfully spots an enemy, it will move its full movement distance toward that enemy, ending at least 1” away, and remaining Hidden. If the Stalker is In Extremis, it will instead move to within 1” of the enemy and attack that enemy with its additional action. If it fails to spot an enemy, it will remain in its current position and remain Hidden.");

    }

    private static void outputRavager(final Cell cell, final Table table) {
        cell.add(new Paragraph("A Ravager first activates according to its Acuity. " +
                "A Ravager activates again at its Acuity -10. " +
                "A Ravager will also activate again if an enemy ranged attack misses. " +
                "In this event, it will make a bonus activation immediately after the conclusion of the activating model that missed and it will attack the target that missed with its Basic Attack. " +
                "A Ravager may never activate more than 3 times during any turn."));

        table.addCell("1.");
        table.addCell("If the Ravager starts its activation In Extremis, it will suffer 2D6 damage and take an extra action during its activation. It will use this action as per the order below.");
        table.addCell("2.");
        table.addCell("If the Ravager starts its activation within 1” of an enemy and it has an area attack option available to it, it will utilize that attack. Then it will move its movement distance away from that enemy, ending its move at least 6” away from all enemies if possible, and ending in cover if possible.");
        table.addCell("3.");
        table.addCell("If the Ravager starts its activation within 1” of an enemy and it does not have an area attack option available to it, it will move its movement distance away from that enemy, ending the move at least 6” away from all enemies if possible, and ending in cover if possible. It will then attack the nearest enemy it can see.");
        table.addCell("4.");
        table.addCell("If the Ravager starts its activation outside of 1” of all enemies and it has an area attack option available to it, it will utilize that attack. Then it will move its movement distance, ending the move at least 6” away from all enemies if possible, and ending in cover if possible.");
        table.addCell("5.");
        table.addCell("If the Ravager starts its activation outside of 1” of all enemies and it does not have an area attack option available and it did not move during its last activation, it will move up to its full movement distance to cover, if possible. If no such cover is available, it will move its full movement distance away from the closest enemy. It will then attack the closest enemy within its line of sight.");
        table.addCell("6.");
        table.addCell(" If the Ravager starts its activation outside of 1” of all enemies and it moved during its last activation, it will attack the closest enemy, using an area attack if possible.");
        table.addCell("7.");
        table.addCell("If the Ravager starts its activation and there are no enemies within its line of sight, it will move the minimum distance to bring an enemy within line of sight and make a ranged attack.");
        table.addCell("8.");
        table.addCell("If the Ravager starts its activation and all enemies are Hidden, it will make an Acuity check to spot Hidden enemies (see Hidden, page 28). If it is successful, it will attack the closest revealed target with its ranged weapons. If this is unsuccessful, it will move to the closest terrain that could provide cover and/or block line of sight to the last known enemy location. If the Ravager has additional actions because it is In Extremis, it will repeat this sequence for its additional actions.");

    }
}
