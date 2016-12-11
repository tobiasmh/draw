package tobias.draw.controller;

import tobias.draw.model.Canvas;
import tobias.draw.service.IDrawRectangleService;
import tobias.draw.service.IOutputService;

/**
 * Created by Tobias on 11/12/16.
 *
 * Performs validation logic on draw rectange instructions.
 */
public class DrawRectangleController implements IDrawRectangleController {

    private IDrawRectangleService drawRectangleService;

    private final IOutputService outputService;

    public DrawRectangleController(IDrawRectangleService drawRectangleService, IOutputService outputService) {
        this.drawRectangleService = drawRectangleService;
        this.outputService = outputService;
    }

    @Override
    public Canvas handle(Canvas canvas, String[] instructionArray) {

        if (instructionArray.length < 5) {

            this.outputService.output("The top left X, top left Y, bottom right X and bottom right Y positions are all required to draw a rectangle. " +
                    "Use the ? command for more information.");
            return canvas;

        } else if (instructionArray.length > 5) {

            this.outputService.output("Only the top left X, top left Y, bottom right X and bottom right Y positions are required to draw a rectangle. " +
                    "Use the ? command for more information.");
            return canvas;

        } else if (canvas == null) {

            this.outputService.output("A canvas needs to be created before a rectangle can be drawn. " +
                    "Use the ? command for more information.");
            return null;

        }

        try {

            int x1 = Integer.parseInt(instructionArray[1]);
            int y1 = Integer.parseInt(instructionArray[2]);
            int x2 = Integer.parseInt(instructionArray[3]);
            int y2 = Integer.parseInt(instructionArray[4]);

            int widthMaximumBoundary = canvas.getWidth() - 2;
            int heightMaximumBoundary = canvas.getHeight() - 2;

            int borderSize = 1;
            if (x1 < borderSize || x1 > widthMaximumBoundary || y1 < borderSize || y1 > heightMaximumBoundary
                    || x2 < borderSize || x2 > widthMaximumBoundary || y2 < borderSize || y2 > heightMaximumBoundary ) {

                throw new NumberFormatException();
            }

            if (x2 < x1 || y2 < y1) {

                this.outputService.output("Rectangle positions must be provided in top left / bottom right format. " +
                        "Use the ? command for more information.");
                return canvas;
            }

            canvas = drawRectangleService.drawRectangle(canvas, x1, y1, x2, y2);
            String[] output = canvas.getPrintRepresentation();
            this.outputService.output(String.join("\n", output));

            return canvas;

        } catch (NumberFormatException e) {
            this.outputService.output("Rectangle positions must be within the bounds of the drawable area in the canvas. " +
                    "Use the ? command for more information.");
            return canvas;
        }
    }
}
