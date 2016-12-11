package tobias.draw.controller;

import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.api.runners.ZohhakRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import tobias.draw.BaseTest;
import tobias.draw.model.Canvas;
import tobias.draw.service.IFillCanvasService;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Tobias on 11/12/16.
 *
 */
@RunWith(ZohhakRunner.class)
public class FillCanvasControllerTest extends BaseTest {

    private IFillCanvasService fillCanvasService = mock(IFillCanvasService.class);

    private FillCanvasController fillCanvasController;

    @Before
    public void setUp() {

        this.fillCanvasController = new FillCanvasController(this.fillCanvasService, outputService);
    }

    @Test
    public void HandleInput_FillCanvas_ServiceIsCalledWithCorrectParametersAndOutputIsCorrect() {

        String[] mockData = {
                "-----",
                "|xxx|",
                "|xox|",
                "|xxx|",
                "-----"
        };
        Canvas mockCreateCanvas = mock(Canvas.class);
        when(mockCreateCanvas.getHeight()).thenReturn(5);
        when(mockCreateCanvas.getWidth()).thenReturn(5);

        Canvas mockFillDrawnCanvas = mock(Canvas.class);
        when(mockFillDrawnCanvas.getHeight()).thenReturn(5);
        when(mockFillDrawnCanvas.getWidth()).thenReturn(5);

        when(this.fillCanvasService.fill(mockCreateCanvas, 2, 2, 'o')).thenReturn(mockFillDrawnCanvas);
        when(mockFillDrawnCanvas.getPrintRepresentation()).thenReturn(mockData);

        this.fillCanvasController.handle(mockCreateCanvas, new String[]{ "B", "2", "2", "o"});

        verify(this.fillCanvasService).fill(mockCreateCanvas, 2, 2, 'o');
        verify(mockFillDrawnCanvas, times(1)).getPrintRepresentation();

        assertThat(getLastOutput(), is(equalTo(String.join("\n", mockData))));

    }

    @Test
    public void HandleInput_FillCanvasWithoutFirstCreatingCanvas_CorrectErrorMessageIsReturned() {

        this.fillCanvasController.handle(null, new String[]{"B", "2", "2", "o"});

        assertThat(getLastOutput(), is(equalTo("A canvas needs to be created before a fill can take place. " +
                "Use the ? command for more information.")));
    }

    @TestWith({
            "-1, 2", // x is negative
            "11, 2", // x exceeds width
            "2, -2", // x is negative
            "2, 10" // x exceeds width
    })
    public void HandleInput_FillCanvasWithInvalidPositions_CorrectErrorMessageIsReturned(String width, String height) {

        Canvas mockCanvas = mock(Canvas.class);
        when(mockCanvas.getHeight()).thenReturn(10);
        when(mockCanvas.getWidth()).thenReturn(10);

        this.fillCanvasController.handle(mockCanvas, new String[]{"B", width, height, "o"});

        assertThat(getLastOutput(), is(equalTo("The fill start positions must be within the bounds of the canvas. " +
                "Use the ? command for more information.")));
    }

    @Test
    public void HandleInput_FillCanvasWithTooFewParameters_CorrectErrorMessageIsReturned() {

        this.fillCanvasController.handle(mock(Canvas.class), new String[]{"B", "2", "o"});

        assertThat(getLastOutput(), is(equalTo("The x position, y position and color character are required " +
                "for a fill. Use the ? command for more information.")));
    }

    @Test
    public void HandleInput_FillCanvasWithTooManyParameters_CorrectErrorMessageIsReturned() {

        this.fillCanvasController.handle(mock(Canvas.class), new String[]{"B", "2", "2", "o", "1"});

        assertThat(getLastOutput(), is(equalTo("Only three arguments, the x position, y position and color " +
                "character are required for a fill. Use the ? command for more information.")));
    }

    @Test
    public void HandleInput_FillCanvasColorIsNotACharacter_CorrectErrorMessageIsReturned() {

        this.fillCanvasController.handle(mock(Canvas.class), new String[]{"B", "2", "2", "ab"});

        assertThat(getLastOutput(), is(equalTo("Only a single character can be provided for a fill. " +
                "Use the ? command for more information.")));
    }
}
