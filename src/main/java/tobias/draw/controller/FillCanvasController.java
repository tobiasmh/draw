package tobias.draw.controller;

import tobias.draw.model.Canvas;
import tobias.draw.service.IFillCanvasService;
import tobias.draw.service.IOutputService;

/**
 * Created by Tobias on 11/12/16.
 *
 * Performs validation logic on fill canvas instructions.
 */
public class FillCanvasController implements IFillCanvasController {

    private IFillCanvasService fillCanvasService;

    private final IOutputService outputService;

    public FillCanvasController(IFillCanvasService fillCanvasService, IOutputService outputService) {
        this.fillCanvasService = fillCanvasService;
        this.outputService = outputService;
    }

    @Override
    public Canvas handle(Canvas canvas, String[] instructionArray) {

        if (instructionArray.length < 4) {

            this.outputService.output("The x position, y position and color character are required for a fill. " +
                    "Use the ? command for more information.");
            return canvas;

        } else if (instructionArray.length > 4) {

            this.outputService.output("Only three arguments, the x position, y position and color character are " +
                    "required for a fill. Use the ? command for more information.");
            return canvas;

        } else if (canvas == null) {

            this.outputService.output("A canvas needs to be created before a fill can take place. " +
                    "Use the ? command for more information.");
            return null;

        }

        try {

            int x = Integer.parseInt(instructionArray[1]);
            int y = Integer.parseInt(instructionArray[2]);

            if (instructionArray[3].length() > 1) {
                this.outputService.output("Only a single character can be provided for a fill. " +
                        "Use the ? command for more information.");
                return canvas;
            }

            char fillCharacter = instructionArray[3].charAt(0);

            if (x < 0 || x >= canvas.getWidth() || y < 0  || y >= canvas.getHeight()) {

                throw new NumberFormatException();
            }

            canvas = fillCanvasService.fill(canvas, x, y, fillCharacter);
            String[] output = canvas.getPrintRepresentation();
            this.outputService.output(String.join("\n", output));

            return canvas;

        } catch (NumberFormatException e) {

            this.outputService.output("The fill start positions must be within the bounds of the canvas. " +
                    "Use the ? command for more information.");
            return canvas;
        }
    }
}
