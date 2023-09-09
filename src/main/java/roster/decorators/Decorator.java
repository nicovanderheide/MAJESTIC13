package roster.decorators;

import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Paragraph;

public class Decorator { //NOPMD - suppressed UseUtilityClass - This method is extended

    public static Cell createCell(final String key, final Object text, final int columns) {
        final Cell cell = new Cell(1, columns);
        cell.add(new Paragraph().add(new Paragraph(key).setBold()).add(new Paragraph(String.valueOf(text))));
        return cell;
    }

    public static Cell createCell(final String text, final int columns) {
        final Cell cell = new Cell(1, columns);
        cell.add(new Paragraph(text));
        return cell;
    }

    public static Cell createCell(final String text) {
        return createCell(text, 1);
    }


    public static Cell spacer(final int rows, final int cols) {
        final Cell cell = new Cell(rows, cols);
        cell.setBorder(Border.NO_BORDER);
        cell.add(new Div().setHeight(5));
        return cell;
    }

    public static Cell spacer(final int cols) {
        return spacer(1, cols);
    }

    public static Paragraph createParagraph(final String text) {
        return new Paragraph(text);
    }
}
