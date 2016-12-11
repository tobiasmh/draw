package tobias.draw.service;

import tobias.draw.model.Canvas;
import tobias.draw.model.Point;

/**
 * Created by Tobias on 11/12/16.
 *
 * Interface provided for future extensibility.
 */
public interface IDrawLineService {

    Canvas drawLine(Canvas canvas, int x1, int y1, int x2, int y2);

}
