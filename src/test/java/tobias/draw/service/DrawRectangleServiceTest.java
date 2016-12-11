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
public class DrawRectangleServiceTest {

    private CreateCanvasService createCanvasService = new CreateCanvasService();

    private DrawRectangleService drawRectangleService = new DrawRectangleService();

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
            "1, 2, 2, 5"  // Y2 PositionGreaterThanHeightMinusTwo
    })
    public void DrawRectangle_InvalidInputExceedingCanvasBounds_RuntimeExceptionRaised(int x1, int y1, int x2, int y2) {

        this.thrown.expect(RuntimeException.class);
        this.thrown.expectMessage("Recentage positions must be within the bounds of the drawable area in the " +
                "canvas.");
        Canvas canvas = this.createCanvasService.createCanvas(3, 3);
        this.drawRectangleService.drawRectangle(canvas, x1, y1, x2, y2);
    }

    @TestWith({
            "5, 1, 3, 1", // x Position 2 is to the left of x Position 1
            "1, 4, 1, 1"  // Y Position 2 is above Y Position 1
    })
    public void DrawRectangle_InvalidInputNegativePosition_RuntimeExceptionRaised(int x1, int y1, int x2, int y2) {

        this.thrown.expect(RuntimeException.class);
        this.thrown.expectMessage("Rectangles positions must be provided in top left / bottom right format.");
        Canvas canvas = this.createCanvasService.createCanvas(10, 10);
        this.drawRectangleService.drawRectangle(canvas, x1, y1, x2, y2);
    }

    @Test
    public void DrawRectangle_ValidRectangleOnSinglePoint_CanvasIsReturnedWithCorrectPoints() {

        String[] expectedCanvasDataString = {
                "----------",
                "|        |",
                "|        |",
                "|        |",
                "|        |",
                "| x      |",
                "|        |",
                "|        |",
                "|        |",
                "----------"
        };

        Point[][] expectedCanvasData = TestUtil.createPointDatafromStringArray(expectedCanvasDataString);

        Canvas canvas = this.createCanvasService.createCanvas(10, 8);
        canvas = this.drawRectangleService.drawRectangle(canvas, 2, 5, 2, 5);

        assertThat(canvas.getCanvasData(), is(equalTo(expectedCanvasData)));
    }

    @Test
    public void DrawRectangle_ValidRectangle_CanvasIsReturnedWithCorrectPoints() {

        String[] expectedCanvasDataString = {
                "----------",
                "|        |",
                "|        |",
                "|        |",
                "|        |",
                "| xxxx   |",
                "| x  x   |",
                "| x  x   |",
                "| xxxx   |",
                "----------"
        };

        Point[][] expectedCanvasData = TestUtil.createPointDatafromStringArray(expectedCanvasDataString);

        Canvas canvas = this.createCanvasService.createCanvas(10, 8);
        canvas = this.drawRectangleService.drawRectangle(canvas, 2, 5, 5, 8);

        assertThat(canvas.getCanvasData(), is(equalTo(expectedCanvasData)));
    }

    @Test
    public void DrawRectangle_OverLappingRectangles_CanvasIsReturnedWithCorrectPoints() {

        String[] expectedCanvasDataString = {
                "----------",
                "|        |",
                "|        |",
                "|        |",
                "|   xxxxx|",
                "| xxxx  x|",
                "| x xxxxx|",
                "| x  x   |",
                "| xxxx   |",
                "----------"
        };

        Point[][] expectedCanvasData = TestUtil.createPointDatafromStringArray(expectedCanvasDataString);

        Canvas canvas = this.createCanvasService.createCanvas(10, 8);
        canvas = this.drawRectangleService.drawRectangle(canvas, 2, 5, 5, 8);
        canvas = this.drawRectangleService.drawRectangle(canvas, 4, 4, 8, 6);

        assertThat(canvas.getCanvasData(), is(equalTo(expectedCanvasData)));
    }

}
