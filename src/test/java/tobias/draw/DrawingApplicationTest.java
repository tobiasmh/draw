package tobias.draw;

import com.googlecode.zohhak.api.runners.ZohhakRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import tobias.draw.controller.ICreateCanvasController;
import tobias.draw.controller.IDrawLineController;
import tobias.draw.controller.IDrawRectangleController;
import tobias.draw.controller.IFillCanvasController;
import tobias.draw.model.Canvas;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * Created by Tobias on 10/12/16.
 *
 */
@RunWith(ZohhakRunner.class)
public class DrawingApplicationTest extends BaseTest {

    private DrawingApplication drawingApplication;

    private ICreateCanvasController createCanvasController = mock(ICreateCanvasController.class);

    private IDrawLineController drawLineController = mock(IDrawLineController.class);

    private IDrawRectangleController drawRectangleController = mock(IDrawRectangleController.class);

    private IFillCanvasController fillCanvasController = mock(IFillCanvasController.class);

    @Before
    public void setUp() {

        this.drawingApplication = new DrawingApplication(createCanvasController, drawLineController,
                drawRectangleController, fillCanvasController, outputService);
    }

    @Test
    public void HandleInput_InvalidCommand_CorrectErrorMessageIsReturned() {

        sendToConsole("X", "Q");
        this.drawingApplication.run();

        assertThat(getLastOutput(), is(equalTo("Unknown command. Please type ? and press enter for available commands, or Q to quit.")));
    }

    @Test
    public void HandleInput_HelpCommand_HelpInformationIsReturned() {

        String[] expectedResponse = DrawingApplication.AVAILABLE_COMMANDS;
        sendToConsole("?", "Q");
        this.drawingApplication.run();

        assertThat(getLastOutput(), is(equalTo(""+String.join("\n", expectedResponse))));
    }

    @Test
    public void HandleInput_CreateCanvasCommand_CanvasServicedIsCalledWithCorrectParameters() {

        sendToConsole("C 1 1", "Q");
        this.drawingApplication.run();

        ArgumentCaptor<String[]> argumentCaptor = ArgumentCaptor.forClass(String[].class);
        verify(this.createCanvasController).handle(any(Canvas.class), argumentCaptor.capture());

        assertThat(argumentCaptor.getValue()[0], is(equalTo("C")));
    }

    @Test
    public void HandleInput_DrawLineCommand_CanvasServicedIsCalledWithCorrectParameters() {

        sendToConsole("L 1 1 1 1", "Q");
        this.drawingApplication.run();

        ArgumentCaptor<String[]> argumentCaptor = ArgumentCaptor.forClass(String[].class);
        verify(this.drawLineController).handle(any(Canvas.class), argumentCaptor.capture());

        assertThat(argumentCaptor.getValue()[0], is(equalTo("L")));
    }

    @Test
    public void HandleInput_DrawRectangleCommand_CanvasServicedIsCalledWithCorrectParameters() {

        sendToConsole("R 1 1 1 1", "Q");
        this.drawingApplication.run();

        ArgumentCaptor<String[]> argumentCaptor = ArgumentCaptor.forClass(String[].class);
        verify(this.drawRectangleController).handle(any(Canvas.class), argumentCaptor.capture());

        assertThat(argumentCaptor.getValue()[0], is(equalTo("R")));
    }

    @Test
    public void HandleInput_FillCanvasCommand_CanvasServicedIsCalledWithCorrectParameters() {

        sendToConsole("B 1 1", "Q");
        this.drawingApplication.run();

        ArgumentCaptor<String[]> argumentCaptor = ArgumentCaptor.forClass(String[].class);
        verify(this.fillCanvasController).handle(any(Canvas.class), argumentCaptor.capture());

        assertThat(argumentCaptor.getValue()[0], is(equalTo("B")));
    }

}
