package tobias.draw;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Import;
import tobias.draw.controller.*;
import tobias.draw.model.Canvas;
import tobias.draw.configuration.DrawingConfiguration;
import tobias.draw.service.IOutputService;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Tobias on 10/12/16.
 *
 *  Application Entry Point. Behaves as a controller and is responsible for validating input and forwarding requests
 *  to the appropriate service or delegated controller.
 */
@Import(value = {DrawingConfiguration.class})
public class DrawingApplication implements CommandLineRunner {

    static final String[] AVAILABLE_COMMANDS = {"Available Commands:",
        "",
        "Create Canvas: C {Width}:Integer {Height}:Integer. ",
        "Example: C 20 4",
        "Example Result: A new canvas created 20x4 in size (The top border is not included in the size but left and " +
                "right side borders are. See README.md.)",
        "--------------------",
        "|                  |",
        "|                  |",
        "|                  |",
        "|                  |",
        "--------------------",
        "Note: The width and height must be greater than zero, and less than the maximum value of an Integer. " +
                "Running this command when a canvas already exists will replace the existing canvas.",
        "",
        "Draw Line: L {Line start X position}:Integer {Line start Y position}:Integer {Line end X position}:Integer " +
                "{Line end Y position}:Integer.",
        "Example: L 1 2 6 2",
        "Example Result: A new line start at point 1,2 and ending at point 6,2",
        "--------------------",
        "|                  |",
        "|xxxxxx            |",
        "|                  |",
        "|                  |",
        "--------------------",
        "Note: A canvas must first be created, and the line endpoints must be within the bounds of the canvas area. " +
                "Only vertical or horizontal lines are supported. The line points must be on the same orthogonal axis",
        "",
        "Draw Rectangle: R {Top left hand corner X position}: Integer {Top left hand corner Y position}: Integer " +
                "{Bottom right hand corner X position}: Integer {Bottom right hand corner Y position}: Integer",
        "Example: R 14 1 18 3",
        "Example Result: A rectangle with its top left hand corner at 14,1 and the bottom right hand corner at 18,3",
        "--------------------",
        "|             xxxxx|",
        "|             x   x|",
        "|             xxxxx|",
        "|                  |",
        "--------------------",
        "Note: The bounds of the recentangle must be within the bounds of the canvas area.",
        "",
        "Fill empty area surrounding selected point: F {Selected point X position}:Integer " +
                "{Selected point Y position}:Integer {Fill character}:Character",
        "Example: F 10 3 o",
        "Example Result: The empty area surrounding and inclusive of the point 10,3 is filled with the o character",
        "--------------------",
        "|oooooooooooooxxxxx|",
        "|xxxxxxooooooox   x|",
        "|     xoooooooxxxxx|",
        "|     xoooooooooooo|",
        "--------------------",
        "Note if your point is already covered in a line or border then no fill will take effect. A fill needs to start " +
                "on an empty points.",
        "",
        "Quit: Q"
    };

    private final ICreateCanvasController createCanvasController;

    private final IDrawLineController drawLineController;

    private final IDrawRectangleController drawRectangleController;

    private final IFillCanvasController fillCanvasController;

    private final IOutputService outputService;

    public DrawingApplication(
            ICreateCanvasController createCanvasController,
            IDrawLineController drawLineController,
            IDrawRectangleController drawRectangleController,
            IFillCanvasController fillCanvasController, IOutputService outputService) {

        this.createCanvasController = createCanvasController;
        this.drawLineController = drawLineController;
        this.drawRectangleController = drawRectangleController;
        this.fillCanvasController = fillCanvasController;
        this.outputService = outputService;
    }

    private static final List<String> VALID_FUNCTIONS = Arrays.asList("C", "L", "R", "B", "Q", "?");

    public static void main(String[] args) {

        System.out.println("The drawing application is starting up. Please wait.");
        SpringApplication.run(DrawingApplication.class, args);

    }

    @Override
    public void run(String... args) {

        this.outputService.output("The drawing application is now ready to receive your instructions. Type ? and enter for help.");

        Scanner scanner = new Scanner(System.in);
        Canvas canvas = null;

        while (true) {

            String instruction = scanner.nextLine().trim();

            String[] instructionArray = instruction.split(" ");

            String command = instructionArray[0];

            if (command.length() != 1 || !VALID_FUNCTIONS.contains(command)) {

                this.outputService.output("Unknown command. Please type ? and press enter for available commands, or Q to quit.");

            } else if (command.equals("?")) {

                this.outputService.output(String.join("\n", AVAILABLE_COMMANDS));

            } else if (command.equals("C")) {

                canvas = this.createCanvasController.handle(canvas, instructionArray);

            } else if (command.equals("L")) {

                canvas = this.drawLineController.handle(canvas, instructionArray);

            } else if (command.equals("R")) {

                canvas = this.drawRectangleController.handle(canvas, instructionArray);

            } else if (command.equals("B")) {

                canvas = this.fillCanvasController.handle(canvas, instructionArray);

            } else if (command.equals("Q")) {

                break;

            }
        }
    }

}
