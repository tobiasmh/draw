package tobias.draw.model;

import org.junit.Test;
import tobias.draw.service.CreateCanvasService;
import tobias.draw.service.DrawLineService;
import tobias.draw.service.DrawRectangleService;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by Tobias on 10/12/16.
 *
 */
public class CanvasTest {

    private CreateCanvasService createCanvasService = new CreateCanvasService();

    private DrawLineService drawLineService = new DrawLineService();

    private DrawRectangleService drawRectangleService = new DrawRectangleService();

    @Test
    public void Constructor_DerivedWidthAndHeight_WidthAndHeightAreDerivedCorrectly() {

        Point[][] canvasData = new Point[5][7];
        Canvas canvas = new Canvas(canvasData);

        assertThat(canvas.getWidth(), is(equalTo(5)));
        assertThat(canvas.getHeight(), is(equalTo(7)));

    }

    @Test
    public void GetPrintRepresentation_ValidCanvas_CorrectResult() {

        String[] expectedCanvasString = {
                "----------",
                "|        |",
                "| xxxx   |",
                "|        |",
                "|        |",
                "| xxxx   |",
                "| x  x   |",
                "| x  x   |",
                "| xxxx   |",
                "----------"
        };

        Canvas canvas = this.createCanvasService.createCanvas(10, 8);
        canvas = this.drawRectangleService.drawRectangle(canvas, 2, 5, 5, 8);
        canvas = this.drawLineService.drawLine(canvas, 2, 2, 5, 2);
        String[] result = canvas.getPrintRepresentation();

        assertThat(result, is(equalTo(expectedCanvasString)));
    }

}
