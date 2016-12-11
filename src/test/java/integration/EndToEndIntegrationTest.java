package integration;

import org.junit.*;

import java.io.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by Tobias on 11/12/16.
 *
 * An end to end integration test designed to perform actions against the application as a user would in the console.
 */
@Ignore // This test should be run explicitly
public class EndToEndIntegrationTest {

    private static Boolean buildSucceeded = false;

    // Build before the tests run
    static {

        ProcessBuilder pb = new ProcessBuilder("mvn", "package");
        pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        pb.redirectError(ProcessBuilder.Redirect.INHERIT);

        try {

            Process process = pb.start();
            process.waitFor();
            buildSucceeded = process.exitValue() == 0;

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private BufferedReader inputReader;
    private PrintStream printOutputStream;
    private Process process;

    @Before
    public void setup() throws Exception {

        assertThat(buildSucceeded, is(equalTo(true)));

        ProcessBuilder pb = new ProcessBuilder("java", "-jar", "target/draw-1.0.jar");
        this.process = pb.start();
        this.inputReader = new BufferedReader(new InputStreamReader(this.process.getInputStream()));
        this.printOutputStream = new PrintStream(this.process.getOutputStream());

        waitForStartup();
    }

    @After
    public void teardown() throws Exception {
        this.inputReader.close();
        this.printOutputStream.close();
    }

    private void waitForStartup() throws Exception {

        String line = this.inputReader.readLine();
        if (line != null && !line.equals("The drawing application is starting up. Please wait.")) {
            throw new RuntimeException("Unexpected output from application");
        }
        line = this.inputReader.readLine();
        if (line != null && !line.equals("The drawing application is now ready to receive your instructions. Type ? and enter for help.")) {
            throw new RuntimeException("Unexpected output from application");
        }

    }

    private String[] readLines(int numberOfLines, int skipLines) throws Exception {

        String[] result = new String[numberOfLines - skipLines];

        for (int i = 0; i < numberOfLines; i++) {

            String line = this.inputReader.readLine();
            System.out.println(line);

            if (i >= skipLines) {

                result[i - skipLines] = line;
            }
        }
        return result;
    }

    @Test
    public void ApplicationInteraction_CreateCanvas_CanvasCreatedCorrectly() throws Exception {

        String expectedOutput = "--------------------" +
                                "|                  |" +
                                "|                  |" +
                                "|                  |" +
                                "|                  |" +
                                "--------------------";

        this.printOutputStream.println("C 20 4");
        this.printOutputStream.flush();
        String[] result = readLines(6, 0);

        assertThat(String.join("", result), is(equalTo(expectedOutput)));

    }

    @Test
    public void ApplicationInteraction_DrawSingleHorizontalLine_CanvasCreatedCorrectly() throws Exception {

        String expectedOutput = "--------------------" +
                                "|                  |" +
                                "|xxxxxx            |" +
                                "|                  |" +
                                "|                  |" +
                                "--------------------";

        this.printOutputStream.println("C 20 4");
        this.printOutputStream.println("L 1 2 6 2");
        this.printOutputStream.flush();
        String[] result = readLines(12, 6);

        assertThat(String.join("", result), is(equalTo(expectedOutput)));

    }

    @Test
    public void ApplicationInteraction_DrawSingleVerticalLine_CanvasCreatedCorrectly() throws Exception {

        String expectedOutput =
                "--------------------" +
                "|                  |" +
                "|xxxxxx            |" +
                "|     x            |" +
                "|     x            |" +
                "--------------------";

        this.printOutputStream.println("C 20 4");
        this.printOutputStream.println("L 1 2 6 2");
        this.printOutputStream.println("L 6 3 6 4");
        this.printOutputStream.flush();
        String[] result = readLines(18, 12);

        assertThat(String.join("", result), is(equalTo(expectedOutput)));

    }

    @Test
    public void ApplicationInteraction_DrawRectangle_CanvasCreatedCorrectly() throws Exception {

        String expectedOutput =
        "--------------------" +
        "|             xxxxx|" +
        "|xxxxxx       x   x|" +
        "|     x       xxxxx|" +
        "|     x            |" +
        "--------------------";

        this.printOutputStream.println("C 20 4");
        this.printOutputStream.println("L 1 2 6 2");
        this.printOutputStream.println("L 6 3 6 4");
        this.printOutputStream.println("R 14 1 18 3");
        this.printOutputStream.flush();
        String[] result = readLines(24, 18);

        assertThat(String.join("", result), is(equalTo(expectedOutput)));

    }

    @Test
    public void ApplicationInteraction_FillCanvas_CanvasCreatedCorrectly() throws Exception {

        String expectedOutput =
                "--------------------" +
                "|oooooooooooooxxxxx|" +
                "|xxxxxxooooooox   x|" +
                "|     xoooooooxxxxx|" +
                "|     xoooooooooooo|" +
                "--------------------";

        this.printOutputStream.println("C 20 4");
        this.printOutputStream.println("L 1 2 6 2");
        this.printOutputStream.println("L 6 3 6 4");
        this.printOutputStream.println("R 14 1 18 3");
        this.printOutputStream.println("B 10 3 o");
        this.printOutputStream.flush();
        String[] result = readLines(30, 24);

        assertThat(String.join("", result), is(equalTo(expectedOutput)));

    }

    @Test
    public void ApplicationInteraction_ApplicationQuit_ExitCodeZeroReturned() throws Exception {

        this.printOutputStream.println("C 20 4");
        this.printOutputStream.println("L 1 2 6 2");
        this.printOutputStream.println("L 6 3 6 4");
        this.printOutputStream.println("R 15 1 18 3");
        this.printOutputStream.println("B 10 3 o");
        this.printOutputStream.println("Q");
        this.printOutputStream.flush();

        this.process.waitFor();

        int exitCode = this.process.exitValue();

        assertThat(exitCode, is(equalTo(0)));

    }

}
