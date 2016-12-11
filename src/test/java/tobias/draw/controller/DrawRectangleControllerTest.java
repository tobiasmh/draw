package tobias.draw.controller;

import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.api.runners.ZohhakRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import tobias.draw.BaseTest;
import tobias.draw.model.Canvas;
import tobias.draw.service.IDrawRectangleService;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Tobias on 11/12/16.
 *
 */
@RunWith(ZohhakRunner.class)
public class DrawRectangleControllerTest extends BaseTest {

    private IDrawRectangleService canvasService = mock(IDrawRectangleService.class);

    private DrawRectangleController drawRectangleController;

    @Before
    public void setUp() {

        this.drawRectangleController = new DrawRectangleController(this.canvasService, outputService);
    }

    @Test
    public void HandleInput_DrawRectangle_ServiceIsCalledWithCorrectParametersAndOutputIsCorrect() {

        String[] mockData = {
                "-----",
                "|xxx|",
                "|x x|",
                "|xxx|",
                "-----"
        };
        Canvas mockCreateCanvas = mock(Canvas.class);
        when(mockCreateCanvas.getHeight()).thenReturn(5);
        when(mockCreateCanvas.getWidth()).thenReturn(5);

        Canvas mockRectangleDrawnCanvas = mock(Canvas.class);
        when(mockRectangleDrawnCanvas.getHeight()).thenReturn(5);
        when(mockRectangleDrawnCanvas.getWidth()).thenReturn(5);

        when(this.canvasService.drawRectangle(any(Canvas.class), eq(1), eq(1), eq(3), eq(3)))
                .thenReturn(mockRectangleDrawnCanvas);
        when(mockRectangleDrawnCanvas.getPrintRepresentation()).thenReturn(mockData);
        this.drawRectangleController.handle(mockCreateCanvas, new String[]{"R", "1", "1", "3", "3"});

        verify(this.canvasService).drawRectangle(mockCreateCanvas, 1, 1, 3, 3);
        verify(mockRectangleDrawnCanvas, times(1)).getPrintRepresentation();

        assertThat(getLastOutput(), is(equalTo(String.join("\n", mockData)+"")));

    }

    @Test
    public void HandleInput_DrawRectangleWithoutFirstCreatingCanvas_CorrectErrorMessageIsReturned() {

        this.drawRectangleController.handle(null, new String[]{"R", "10", "15", "15", "15"});

        assertThat(getLastOutput(), is(equalTo("A canvas needs to be created before a rectangle can be drawn. " +
                "Use the ? command for more information.")));
    }

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
    public void HandleInput_DrawRectangleWithInvalidPositions_CorrectErrorMessageIsReturned(String x1, String y1, String x2, String y2) {

        Canvas mockCanvas = mock(Canvas.class);
        when(mockCanvas.getHeight()).thenReturn(5);
        when(mockCanvas.getWidth()).thenReturn(5);

        this.drawRectangleController.handle(mockCanvas, new String[]{"R", x1, y1, x2, y2});

        assertThat(getLastOutput(), is(equalTo("Rectangle positions must be within the bounds of the drawable " +
                "area in the canvas. Use the ? command for more information.")));
    }

    @TestWith({
            "5, 1, 3, 1", // x Position 2 is to the left of x Position 1
            "1, 4, 1, 1"  // Y Position 2 is above Y Position 1
    })
    public void HandleInput_DrawRectangleWithNegativePositions_CorrectErrorMessageIsReturned(String x1, String y1,
                                                                                             String x2, String y2) {

        Canvas mockCanvas = mock(Canvas.class);
        when(mockCanvas.getHeight()).thenReturn(10);
        when(mockCanvas.getWidth()).thenReturn(10);

        this.drawRectangleController.handle(mockCanvas, new String[]{"R", x1, y1, x2, y2});

        assertThat(getLastOutput(), is(equalTo("Rectangle positions must be provided in top left / bottom right " +
                "format. Use the ? command for more information.")));
    }

    @Test
    public void HandleInput_DrawRectangleWithTooFewParameters_CorrectErrorMessageIsReturned() {

        this.drawRectangleController.handle(mock(Canvas.class), new String[]{"R", "1", "2", "3"});

        assertThat(getLastOutput(), is(equalTo("The top left X, top left Y, bottom right X and bottom right Y " +
                "positions are all required to draw a rectangle. Use the ? command for more information.")));
    }

    @Test
    public void HandleInput_DrawRectangleWithTooManyParameters_CorrectErrorMessageIsReturned() {

        this.drawRectangleController.handle(mock(Canvas.class), new String[]{"R", "1", "2", "3", "4", "5"});

        assertThat(getLastOutput(), is(equalTo("Only the top left X, top left Y, bottom right X and bottom right " +
                "Y positions are required to draw a rectangle. Use the ? command for more information.")));
    }
}