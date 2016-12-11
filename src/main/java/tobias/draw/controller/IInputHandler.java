package tobias.draw.controller;

import tobias.draw.model.Canvas;

/**
 * Created by Tobias on 11/12/16.
 *
 * Standard interface for processing string array input instructions.
 */
public interface IInputHandler {

    Canvas handle(Canvas canvas, String[] instructionArray);

}
