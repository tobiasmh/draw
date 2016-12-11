package tobias.draw.controller;

import tobias.draw.model.Canvas;
import tobias.draw.service.ICreateCanvasService;
import tobias.draw.service.IOutputService;

/**
 * Created by Tobias on 11/12/16.
 *
 * Performs validation logic on create canvas instructions.
 */
public class CreateCanvasController implements ICreateCanvasController {

    private ICreateCanvasService createCanvasService;

    private final IOutputService outputService;

    public CreateCanvasController(ICreateCanvasService createCanvasService, IOutputService outputService) {

        this.createCanvasService = createCanvasService;
        this.outputService = outputService;
    }

    @Override
    public Canvas handle(Canvas canvas, String[] instructionArray) {

        if (instructionArray.length < 3) {

            this.outputService.output("Both the width and height are required for a canvas. " +
                    "Use the ? command for more information.");
            return canvas;

        } else if (instructionArray.length > 3) {

            this.outputService.output("Only two arguments, the width and height are required for a canvas. " +
                    "Use the ? command for more information.");
            return canvas;

        } try {

            int width = Integer.parseInt(instructionArray[1]);
            int height = Integer.parseInt(instructionArray[2]);

            if (width < 1 || height < 1) {
                throw new NumberFormatException();
            }

            canvas = createCanvasService.createCanvas(width, height);
            String[] output = canvas.getPrintRepresentation();
            this.outputService.output(String.join("\n", output));
            return canvas;

        } catch (NumberFormatException e) {

            this.outputService.output("The width and height of the canvas must both be integer numbers greater than 0. " +
                    "Use the ? command for more information.");
            return canvas;
        }
    }
}
