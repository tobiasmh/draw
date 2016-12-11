package tobias.draw.service;

import tobias.draw.model.Canvas;

/**
 * Created by Tobias on 11/12/16.
 *
 * Interface provided for future extensibility.
 */
public interface ICreateCanvasService {

    Canvas createCanvas(int width, int height);
}
