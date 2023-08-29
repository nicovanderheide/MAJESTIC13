package roster;

import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import data.Team;
import lombok.extern.slf4j.Slf4j;
import parser.YamlReader;
import roster.decorators.BaseDecorator;
import roster.decorators.TeamDecorator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

@Slf4j
public class RosterBuilder {

    public static void generate(final Path crewFile) {

        String outputFile = crewFile.getFileName().toString().replace(".yml", ".pdf");
        log.info("Generating: {}", outputFile);
        try (final PdfDocument pdf = new PdfDocument(new PdfWriter(outputFile));
             final Document document = new Document(pdf, PageSize.A4).setFont(PdfFontFactory.createFont("Courier")).setFontSize(8)
        ) {
            Team team = YamlReader.read(crewFile);
            log.info("TEAM: {}", team);

            TeamDecorator.addTeamPage(document, team);
            document.add(new AreaBreak(PageSize.A4));
            BaseDecorator.addBaseDefinitionPage(document, team.getBase());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
