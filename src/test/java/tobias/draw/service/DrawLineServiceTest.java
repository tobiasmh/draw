package tobias.draw.service;

import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.api.runners.ZohhakRunner;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import tobias.draw.model.Canvas;
import tobias.draw.model.Point;
import tobias.draw.util.TestUtil;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by Tobias on 11/12/16.
 *
 */
@RunWith(ZohhakRunner.class)
public class DrawLineServiceTest {

    private CreateCanvasService createCanvasService = new CreateCanvasService();
    private DrawLineService drawLineService = new DrawLineService();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @TestWith({
            "0, 1, 1, 1", // x1 PositionLessThanOne
            "0, 4, 1, 1", // x1 PositionGreaterThanHeightMinusTwo
            "1, 2, -1, 1", // x2 PositionLessThanOne
            "1, 2, 10, 1", // x2 PositionGreaterThanHeightMinusTwo
            "1, -3, 2, 1", // Y1 PositionLessThanOne
            "1, 6, 2, 1", // Y1 PositionGreaterThanHeightMinusTwo
            "1, 2, 2, 0", // Y2 PositionLessThanOne
            "1, 2, 2, 6"  // Y2 PositionGreaterThanHeightMinusTwo
    })
    public void DrawLine_InvalidInputExceedingCanvasBounds_RuntimeExceptionRaised(int x1, int y1, int x2, int y2) {

        this.thrown.expect(RuntimeException.class);
        this.thrown.expectMessage("Line start positions must be within the bounds of the drawable area in the canvas.");
        Canvas canvas = this.createCanvasService.createCanvas(5, 5);
        this.drawLineService.drawLine(canvas, x1, y1, x2, y2);
    }

    @Test
    public void DrawLine_RightDirection_CanvasIsReturnedWithCorrectPoints() {

        String[] expectedCanvasDataString = {
                "----------",
                "|        |",
                "|        |",
                "|        |",
                "|xxxx    |",
                "|        |",
                "|        |",
                "|        |",
                "|        |",
                "----------"
        };

        Point[][] expectedCanvasData = TestUtil.createPointDatafromStringArray(expectedCanvasDataString);

        Canvas canvas = this.createCanvasService.createCanvas(10, 8);
        canvas = this.drawLineService.drawLine(canvas, 1, 4, 4, 4);

        assertThat(canvas.getCanvasData(), is(equalTo(expectedCanvasData)));
    }

    @Test
    public void DrawLine_LeftDirection_CanvasIsReturnedWithCorrectPoints() {

        String[] expectedCanvasDataString = {
                "----------",
                "|        |",
                "|        |",
                "|        |",
                "|        |",
                "|        |",
                "|        |",
                "|  xxx   |",
                "|        |",
                "----------"
        };

        Point[][] expectedCanvasData = TestUtil.createPointDatafromStringArray(expectedCanvasDataString);

        Canvas canvas = this.createCanvasService.createCanvas(10, 8);
        canvas = this.drawLineService.drawLine(canvas, 3, 7, 5, 7);

        assertThat(canvas.getCanvasData(), is(equalTo(expectedCanvasData)));

    }

    @Test
    public void DrawLine_UpDirection_CanvasIsReturnedWithCorrectPoints() {

        String[] expectedCanvasDataString = {
                "----------",
                "|      x |",
                "|      x |",
                "|      x |",
                "|      x |",
                "|      x |",
                "|      x |",
                "|      x |",
                "|      x |",
                "----------"
        };

        Point[][] expectedCanvasData = TestUtil.createPointDatafromStringArray(expectedCanvasDataString);

        Canvas canvas = this.createCanvasService.createCanvas(10, 8);
        canvas = this.drawLineService.drawLine(canvas, 7, 1, 7 ,8);

        assertThat(canvas.getCanvasData(), is(equalTo(expectedCanvasData)));
    }

    @Test
    public void DrawLine_DownDirection_CanvasIsReturnedWithCorrectPoints() {

        String[] expectedCanvasDataString = {
                "----------",
                "|        |",
                "|        |",
                "|        |",
                "|        |",
                "| x      |",
                "| x      |",
                "|        |",
                "|        |",
                "----------"
        };

        Point[][] expectedCanvasData = TestUtil.createPointDatafromStringArray(expectedCanvasDataString);

        Canvas canvas = this.createCanvasService.createCanvas(10, 8);
        canvas = this.drawLineService.drawLine(canvas, 2, 5, 2 ,6);

        assertThat(canvas.getCanvasData(), is(equalTo(expectedCanvasData)));
    }

    @Test
    public void DrawLine_LineWithPointsNotOnTheSameAxis_RuntimeExceptionRaised() {

        this.thrown.expect(RuntimeException.class);
        this.thrown.expectMessage("Line start and end positions must be on the same orthogonal axis.");
        Canvas canvas = this.createCanvasService.createCanvas(5, 5);
        this.drawLineService.drawLine(canvas, 1, 2, 2, 3);
    }

}
