package roster.decorators;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Table;
import data.Team;

public class TeamDecorator extends Decorator {

    public static void addTeamPage(final Document document, final Team team) {

        document.add(createTeamTable(team));
        document.add(spacer(1));
        if (team.getMembers() != null) {
            team.getMembers().stream().sorted().forEach(member -> {
                document.add(PersonDecorator.addPersonTable(team, member));
                document.add(spacer(1));
            });

        }
    }

    private static Table createTeamTable(final Team team) {
        final Table teamTable = new Table(3).setBorder(Border.NO_BORDER).useAllAvailableWidth();
        teamTable.addCell(createCell("TEAM NAME:", team.getName(), 1));
        teamTable.addCell(createCell("FACTION:", team.getFaction().getName(), 1));
        teamTable.addCell(createCell("TEAM RATING:", team.getRating(), 1));
        teamTable.startNewRow();
        teamTable.addCell(createCell("FACTION BONUS:", team.getFaction().getOutput(), 3));
        teamTable.addCell(createCell("ADVANTAGE:", team.getAdvantage(), 3));
        teamTable.addCell(createCell("BASE BONUS:", team.getBase().getBaseType().getOutput(), 3));

        return teamTable;
    }
}
