package roster.decorators;

import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Paragraph;

public class Decorator {

    static Cell createCell(final String key, final Object text, final int columns) {
        Cell cell = new Cell(1, columns);
        cell.add(new Paragraph().add((new Paragraph(key).setBold())).add(new Paragraph(String.valueOf(text))));
        return cell;
    }
    static Cell createCell(final String text, final int columns) {
        Cell cell = new Cell(1, columns);
        cell.add(new Paragraph(text));
        return cell;
    }

    static Cell createCell(final String text) {
        return createCell(text, 1);
    }


    static Cell spacer(int rows, int cols) {
        Cell cell = new Cell(rows, cols);
        cell.setBorder(Border.NO_BORDER);
        cell.add(new Div().setHeight(5));
        return cell;
    }

    public static Cell spacer(int cols) {
        return spacer(1, cols);
    }
}
