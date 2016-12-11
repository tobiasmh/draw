package tobias.draw.controller;

import tobias.draw.model.Canvas;
import tobias.draw.service.IDrawLineService;
import tobias.draw.service.IOutputService;

/**
 * Created by Tobias on 11/12/16.
 *
 * Performs validation logic on draw line instructions.
 */
public class DrawLineController implements IDrawLineController {

    private IDrawLineService drawLineService;

    private final IOutputService outputService;

    public DrawLineController(IDrawLineService drawLineService, IOutputService outputService) {

        this.drawLineService = drawLineService;
        this.outputService = outputService;
    }

    @Override
    public Canvas handle(Canvas canvas, String[] instructionArray) {

        if (instructionArray.length < 5) {

            this.outputService.output("The start X, start Y, end X and end Y positions are all required to draw a line. " +
                    "Use the ? command for more information.");
            return canvas;

        } else if (instructionArray.length > 5) {

            this.outputService.output("Only start X, start Y, end X and end Y positions are required to draw a line. " +
                    "Use the ? command for more information.");
            return canvas;

        } else if (canvas == null) {

            this.outputService.output("A canvas needs to be created before a line can be drawn. " +
                    "Use the ? command for more information.");
            return null;

        } try {

            int x1 = Integer.parseInt(instructionArray[1]);
            int y1 = Integer.parseInt(instructionArray[2]);
            int x2 = Integer.parseInt(instructionArray[3]);
            int y2 = Integer.parseInt(instructionArray[4]);

            if (x1 != x2 && y1 != y2) {

                this.outputService.output("Line start and end positions must be on the same orthogonal axis.");
                return canvas;
            }

            int widthMaximumBoundary = canvas.getWidth() - 2;
            int heightMaximumBoundary = canvas.getHeight() - 2;

            int borderSize = 1;
            if (x1 < borderSize || x1 > widthMaximumBoundary || y1 < borderSize || y1 > heightMaximumBoundary
                    || x2 < borderSize || x2 > widthMaximumBoundary || y2 < borderSize || y2 > heightMaximumBoundary ) {

                throw new NumberFormatException();
            }

            canvas = drawLineService.drawLine(canvas, x1, y1, x2, y2);
            String[] output = canvas.getPrintRepresentation();
            this.outputService.output(String.join("\n", output));

            return canvas;

        } catch (NumberFormatException e) {

            this.outputService.output("The line positions provided must be an integer and within the bounds of the canvas. " +
                    "Use the ? command for more information.");
            return canvas;
        }
    }

}
