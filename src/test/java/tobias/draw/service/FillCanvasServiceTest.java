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
public class FillCanvasServiceTest {

    private CreateCanvasService createCanvasService = new CreateCanvasService();

    private DrawLineService drawLineService = new DrawLineService();

    private DrawRectangleService drawRectangleService = new DrawRectangleService();

    private FillCanvasService fillCanvasService = new FillCanvasService();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @TestWith({
            "-1, 2", // x is negative
            "11, 2", // x exceeds width
            "2, -2", // x is negative
            "2, 10" // x exceeds width
    })
    public void FillCanvas_InvalidInputOutsideBounds_RuntimeExceptionRaised(int x, int y) {

        this.thrown.expect(RuntimeException.class);
        this.thrown.expectMessage("Fill point must be within the bounds of the canvas.");
        Canvas canvas = this.createCanvasService.createCanvas(8, 8);
        this.fillCanvasService.fill(canvas, x, y, 'O');
    }

    @Test
    public void FillCanvas_ValidInputOnExisitingStructurePoint_CanvasIsNotModified() {

        String[] expectedCanvasDataString = {
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

        Point[][] expectedCanvasData = TestUtil.createPointDatafromStringArray(expectedCanvasDataString);

        Canvas canvas = this.createCanvasService.createCanvas(10, 8);
        canvas = this.drawRectangleService.drawRectangle(canvas, 2, 5, 5, 8);
        canvas = this.drawLineService.drawLine(canvas, 2, 2, 5, 2);
        canvas = this.fillCanvasService.fill(canvas, 2, 6, 'O');

        assertThat(canvas.getCanvasData(), is(equalTo(expectedCanvasData)));
    }

    @Test
    public void FillCanvas_ValidInputOutsideRectangle_CanvasIsReturnedWithCorrectPoints() {

        String[] expectedCanvasDataString = {
                "----------",
                "|OOOOOOOO|",
                "|OxxxxOOO|",
                "|OOOOOOOO|",
                "|OOOOOOOO|",
                "|OxxxxOOO|",
                "|Ox  xOOO|",
                "|Ox  xOOO|",
                "|OxxxxOOO|",
                "----------"
        };

        Point[][] expectedCanvasData = TestUtil.createPointDatafromStringArray(expectedCanvasDataString);

        Canvas canvas = this.createCanvasService.createCanvas(10, 8);
        canvas = this.drawRectangleService.drawRectangle(canvas, 2, 5, 5, 8);
        canvas = this.drawLineService.drawLine(canvas, 2, 2, 5, 2);
        canvas = this.fillCanvasService.fill(canvas, 1, 6, 'O');

        assertThat(canvas.getCanvasData(), is(equalTo(expectedCanvasData)));
    }

    @Test
    public void FillCanvas_ValidInputInsideRectangle_CanvasIsReturnedWithCorrectPoints() {

        String[] expectedCanvasDataString = {
                "----------",
                "|        |",
                "| xxxx   |",
                "|        |",
                "|        |",
                "| xxxx   |",
                "| xOOx   |",
                "| xOOx   |",
                "| xxxx   |",
                "----------"
        };

        Point[][] expectedCanvasData = TestUtil.createPointDatafromStringArray(expectedCanvasDataString);

        Canvas canvas = this.createCanvasService.createCanvas(10, 8);
        canvas = this.drawRectangleService.drawRectangle(canvas, 2, 5, 5, 8);
        canvas = this.drawLineService.drawLine(canvas, 2, 2, 5, 2);
        canvas = this.fillCanvasService.fill(canvas, 3, 7, 'O');

        assertThat(canvas.getCanvasData(), is(equalTo(expectedCanvasData)));
    }

    @Test
    public void FillCanvas_OverwriteExistingFill_CanvasIsReturnedWithCorrectPoints() {

        String[] expectedCanvasDataString = {
                "----------",
                "|        |",
                "| xxxx   |",
                "|        |",
                "|        |",
                "| xxxx   |",
                "| x!!x   |",
                "| x!!x   |",
                "| xxxx   |",
                "----------"
        };

        Point[][] expectedCanvasData = TestUtil.createPointDatafromStringArray(expectedCanvasDataString);

        Canvas canvas = this.createCanvasService.createCanvas(10, 8);
        canvas = this.drawRectangleService.drawRectangle(canvas, 2, 5, 5, 8);
        canvas = this.drawLineService.drawLine(canvas, 2, 2, 5, 2);
        canvas = this.fillCanvasService.fill(canvas, 3, 7, 'O');
        canvas = this.fillCanvasService.fill(canvas, 3, 6, '!');

        assertThat(canvas.getCanvasData(), is(equalTo(expectedCanvasData)));
    }

    @Test
    public void FillCanvas_FillWithMultipleLines_CanvasIsReturnedWithCorrectPoints() {

        String[] expectedCanvasDataString = {
                "----------",
                "|xxxxxxxo|",
                "|oooooooo|",
                "|oxxxxxxx|",
                "|oooooooo|",
                "|xxxxxxxo|",
                "|oooooooo|",
                "|oxxxxxxx|",
                "|oooooooo|",
                "----------"
        };

        Point[][] expectedCanvasData = TestUtil.createPointDatafromStringArray(expectedCanvasDataString);

        Canvas canvas = this.createCanvasService.createCanvas(10, 8);
        canvas = this.drawLineService.drawLine(canvas, 1, 1, 7, 1);
        canvas = this.drawLineService.drawLine(canvas, 2, 3, 8, 3);
        canvas = this.drawLineService.drawLine(canvas, 1, 5, 7, 5);
        canvas = this.drawLineService.drawLine(canvas, 2, 7, 8, 7);

        canvas = this.fillCanvasService.fill(canvas, 8, 8, 'o');

        assertThat(canvas.getCanvasData(), is(equalTo(expectedCanvasData)));
    }

    @Test
    public void FillCanvas_FillOnExistingNonFillPoint_CanvasIsReturnedWithNoModification() {

        String[] expectedCanvasDataString = {
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

        Point[][] expectedCanvasData = TestUtil.createPointDatafromStringArray(expectedCanvasDataString);

        Canvas canvas = this.createCanvasService.createCanvas(10, 8);
        canvas = this.drawRectangleService.drawRectangle(canvas, 2, 5, 5, 8);
        canvas = this.drawLineService.drawLine(canvas, 2, 2, 5, 2);
        canvas = this.fillCanvasService.fill(canvas, 2, 2, 'O');

        assertThat(canvas.getCanvasData(), is(equalTo(expectedCanvasData)));
    }

    @Test
    public void FillCanvas_FillMultipleEnclosedRectangles_CanvasIsReturnedWithCorrectData() {

        String[] expectedCanvasDataString = {
                "----------",
                "| xxxx   |",
                "| xAAx   |",
                "| xxxx   |",
                "|        |",
                "| xxxx   |",
                "| xBBx   |",
                "| xBBx   |",
                "| xxxx   |",
                "----------"
        };

        Point[][] expectedCanvasData = TestUtil.createPointDatafromStringArray(expectedCanvasDataString);

        Canvas canvas = this.createCanvasService.createCanvas(10, 8);
        canvas = this.drawRectangleService.drawRectangle(canvas, 2, 5, 5, 8);
        canvas = this.drawRectangleService.drawRectangle(canvas, 2, 1, 5, 3);
        canvas = this.fillCanvasService.fill(canvas, 3, 2, 'A');
        canvas = this.fillCanvasService.fill(canvas, 3, 6, 'B');

        assertThat(canvas.getCanvasData(), is(equalTo(expectedCanvasData)));
    }

}
