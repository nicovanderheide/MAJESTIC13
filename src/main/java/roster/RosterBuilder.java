package roster;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import data.Member;
import data.Team;
import lombok.extern.slf4j.Slf4j;
import parser.CrewReader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

@Slf4j
public class RosterBuilder {
    public void create(final Path crewFile) {
        String outputFile = crewFile.getFileName().toString() + ".pdf";
        log.info("generating: {}", outputFile);
        try (final PdfDocument pdf = new PdfDocument(new PdfWriter(new File(outputFile)));
             final Document document = new Document(pdf, PageSize.A4)
        ) {
            Team team = CrewReader.read(crewFile);
            log.info("{}", team);
            Table table = new Table(1).useAllAvailableWidth();
            table.setBorder(Border.NO_BORDER);
            table.setMinWidth(UnitValue.createPercentValue(100));

            table.addCell(createTopCell(team));
            if (team.getMembers() != null) {
                for (Member member : team.getMembers()) {
                    table.addCell(addMemberCell(member));
                }
            }
            document.add(table);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Cell createTopCell(final Team team) {
        Cell topCell = new Cell();
        topCell.setBorder(Border.NO_BORDER);
        topCell.setHeight(240);

        return topCell;
    }

    public Cell addMemberCell(final Member member) {

        Cell memberCell = new Cell();
        memberCell.setBorder(Border.NO_BORDER);
        memberCell.setHeight(120);

        memberCell.add(addMemberTable(member));
        return memberCell;
    }


    private Table addMemberTable(final Member member) {
        Table memberTable = new Table(UnitValue.createPercentArray(2));
        memberTable.setMargin(0);
        memberTable.useAllAvailableWidth();
        memberTable.setBorder(Border.NO_BORDER);

        return memberTable;
    }

    private Cell createCell(final String text, final int columns) {
        Cell cell = new Cell(1, columns);
        cell.setBorder(Border.NO_BORDER);
        cell.add(new Paragraph(text));
        cell.setTextAlignment(TextAlignment.CENTER);
        return cell;

    }

    private Cell createCell(final String text) {
        return createCell(text, 1);
    }


    private Cell spacer(int rows, int cols) {
        Cell cell = new Cell(rows, cols);
        cell.setBorder(Border.NO_BORDER);
        cell.add(new Div().setHeight(10));
        return cell;
    }

    private Cell spacer(int cols) {
        return spacer(1, cols);
    }
}
