package tobias.draw.controller;

import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.api.runners.ZohhakRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import tobias.draw.BaseTest;
import tobias.draw.model.Canvas;
import tobias.draw.service.IDrawLineService;

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
public class DrawLineControllerTest extends BaseTest {

    private IDrawLineService canvasService = mock(IDrawLineService.class);

    private DrawLineController drawLineController;

    @Before
    public void setUp() {

        this.drawLineController = new DrawLineController(this.canvasService, outputService);
    }

    @Test
    public void HandleInput_DrawLine_ServiceIsCalledWithCorrectParametersAndOutputIsCorrect() {

        String[] mockData = {
                "-----",
                "|   |",
                "|xxx|",
                "|   |",
                "-----"
        };

        Canvas mockCreateCanvas = mock(Canvas.class);
        when(mockCreateCanvas.getHeight()).thenReturn(5);
        when(mockCreateCanvas.getWidth()).thenReturn(5);

        Canvas mockLineDrawnCanvas = mock(Canvas.class);
        when(mockLineDrawnCanvas.getHeight()).thenReturn(5);
        when(mockLineDrawnCanvas.getWidth()).thenReturn(5);

        when(this.canvasService.drawLine(any(Canvas.class), eq(1), eq(2), eq(3), eq(2)))
                .thenReturn(mockLineDrawnCanvas);
        when(mockLineDrawnCanvas.getPrintRepresentation()).thenReturn(mockData);

        this.drawLineController.handle(mockCreateCanvas, new String[] {"L", "1", "2", "3", "2"});

        verify(this.canvasService).drawLine(mockCreateCanvas, 1, 2, 3, 2);
        verify(mockLineDrawnCanvas).getPrintRepresentation();

        assertThat(getLastOutput(), is(equalTo(String.join("\n", mockData)+"")));

    }

    @Test
    public void HandleInput_DrawLineWithoutFirstCreatingCanvas_CorrectErrorMessageIsReturned() {

        this.drawLineController.handle(null, new String[] {"L", "10", "15", "15", "15"});

        assertThat(getLastOutput(), is(equalTo("A canvas needs to be created before a line can be drawn. " +
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
            "1, 2, 2, 5",  // Y2 PositionGreaterThanHeightMinusTwo
            "A, Z, 3, 3" // Non integer input
    })
    public void HandleInput_DrawLineWithInvalidPositions_CorrectErrorMessageIsReturned(String x1, String y1,
                                                                                       String x2, String y2) {

        Canvas mockCanvas = mock(Canvas.class);
        when(mockCanvas.getHeight()).thenReturn(5);
        when(mockCanvas.getWidth()).thenReturn(5);

        this.drawLineController.handle(mock(Canvas.class), new String[] {"L", x1, y2, x2, y2});

        assertThat(getLastOutput(), is(equalTo("The line positions provided must be an integer and within the " +
                "bounds of the canvas. Use the ? command for more information.")));
    }

    @Test
    public void HandleInput_DrawLineWithTooFewParameters_CorrectErrorMessageIsReturned() {

        this.drawLineController.handle(mock(Canvas.class), new String[] {"L", "1", "2", "3"});

        assertThat(getLastOutput(), is(equalTo("The start X, start Y, end X and end Y positions are all required " +
                "to draw a line. Use the ? command for more information.")));
    }

    @Test
    public void HandleInput_DrawLineWithTooManyParameters_CorrectErrorMessageIsReturned() {

        this.drawLineController.handle(mock(Canvas.class), new String[] {"L", "1", "2", "3", "4", "5"});

        assertThat(getLastOutput(), is(equalTo("Only start X, start Y, end X and end Y positions are required " +
                "to draw a line. Use the ? command for more information.")));
    }

    //
    @Test
    public void HandleInput_LineWithPointsNotOnTheSameAxis_CorrectErrorMessageIsReturned() {

        this.drawLineController.handle(mock(Canvas.class), new String[] {"L", "1", "2", "3", "4"});

        assertThat(getLastOutput(), is(equalTo("Line start and end positions must be on the same orthogonal axis.")));
    }
}