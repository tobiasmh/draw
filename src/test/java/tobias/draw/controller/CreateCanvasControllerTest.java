package tobias.draw.controller;

import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.api.runners.ZohhakRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import tobias.draw.BaseTest;
import tobias.draw.model.Canvas;
import tobias.draw.service.ICreateCanvasService;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Tobias on 11/12/16.
 *
 */
@RunWith(ZohhakRunner.class)
public class CreateCanvasControllerTest extends BaseTest {

    private ICreateCanvasService canvasService = mock(ICreateCanvasService.class);

    private CreateCanvasController createCanvasController;

    @Before
    public void setUp() {

        this.createCanvasController = new CreateCanvasController(this.canvasService, this.outputService);
    }

    @Test
    public void HandleInput_CreateCanvas_ServiceIsCalledWithCorrectParametersAndOutputIsCorrect() {

        String[] mockData = {
                "-----",
                "|   |",
                "|   |",
                "|   |",
                "-----"
        };
        Canvas mockCanvas = mock(Canvas.class);

        when(this.canvasService.createCanvas(3, 3)).thenReturn(mockCanvas);
        when(mockCanvas.getPrintRepresentation()).thenReturn(mockData);

        this.createCanvasController.handle(mockCanvas, new String[] { "C", "3", "3" });

        verify(this.canvasService).createCanvas(3, 3);
        verify(mockCanvas).getPrintRepresentation();

        assertThat(getLastOutput(), is(equalTo(String.join("\n", mockData))));

    }

    @TestWith({
            "0, 1", // Zero width Input
            "1, 0", // Zero height Input
            "-2, 1", // Negative width Input
            "1, -5",  // Negative height Input
            "A, 1" // Non integer input
    })
    public void HandleInput_CreateCanvasWithInvalidSizes_CorrectErrorMessageIsReturned(String width, String height) {

        this.createCanvasController.handle(mock(Canvas.class), new String[] { "C", width, height });

        assertThat(getLastOutput(), is(equalTo("The width and height of the canvas must both be integer numbers " +
                "greater than 0. Use the ? command for more information.")));
    }

    @Test
    public void HandleInput_CreateCanvasWithTooFewParameters_CorrectErrorMessageIsReturned() {

        this.createCanvasController.handle(mock(Canvas.class), new String[] { "C", "1" });

        assertThat(getLastOutput(), is(equalTo("Both the width and height are required for a canvas. " +
                "Use the ? command for more information.")));
    }

    @Test
    public void HandleInput_CreateCanvasWithTooManyParameters_CorrectErrorMessageIsReturned() {

        this.createCanvasController.handle(mock(Canvas.class), new String[] { "C", "1", "2", "3" });

        assertThat(getLastOutput(), is(equalTo("Only two arguments, the width and height are required for a canvas. " +
                "Use the ? command for more information.")));
    }
}