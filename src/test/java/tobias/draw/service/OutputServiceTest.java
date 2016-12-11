package tobias.draw.service;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Tobias on 11/12/16.
 *
 */
public class OutputServiceTest {

    private SystemConsoleOutputService consoleOutputService = new SystemConsoleOutputService();

    @Test
    public void WriteConsoleOutput_ValidOutput_OutputIsCorrectlyWrittenToConsole() {

        ByteArrayOutputStream captureStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(captureStream);
        System.setOut(printStream);

        this.consoleOutputService.output("Test Value");

        assertThat(captureStream.toString(), is(equalTo("Test Value\n")));
    }

}
