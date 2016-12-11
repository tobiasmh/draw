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
public class CreateCanvasServiceTest {

    private ICreateCanvasService createCanvasService = new CreateCanvasService();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @TestWith({
            "0, 1", // Zero Width Input
            "1, 0", // Zero Height Input
            "-2, 1", // Negative Width Input
            "1, -5"  // Negative Height Input
    })
    public void CreateCanvas_InvalidInput_RuntimeExceptionRaised(int width, int height) {

        this.thrown.expect(RuntimeException.class);
        this.thrown.expectMessage("Canvas Height and Width must both be greater than 0");
        this.createCanvasService.createCanvas(width, height);
    }

    @Test
    public void CreateCanvas_ValidInput_CanvasIsReturnedWithCorrectPoints() {

        String[] expectedCanvasDataString = {
                "-----",
                "|   |",
                "|   |",
                "|   |",
                "-----"
        };

        Point[][] expectedCanvasData = TestUtil.createPointDatafromStringArray(expectedCanvasDataString);
        Canvas canvas = this.createCanvasService.createCanvas(5, 3);

        assertThat(canvas.getCanvasData(), is(equalTo(expectedCanvasData)));
    }

}
