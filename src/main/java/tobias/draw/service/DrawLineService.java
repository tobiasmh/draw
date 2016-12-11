package tobias.draw.service;

import tobias.draw.model.Canvas;
import tobias.draw.model.Point;

/**
 * Created by Tobias on 11/12/16.
 *
 */
public class DrawLineService implements IDrawLineService {

    @Override
    public Canvas drawLine(Canvas canvas, int x1, int y1, int x2, int y2) {

        // Ensure that the line will be drawn within the borders.
        int widthMaximumBoundary = canvas.getWidth() - 2;
        int heightMaximumBoundary = canvas.getHeight() - 2;

        int borderSize = 1;
        if (x1 < borderSize || x1 > widthMaximumBoundary || y1 < borderSize || y1 > heightMaximumBoundary
                || x2 < borderSize || x2 > widthMaximumBoundary || y2 < borderSize || y2 > heightMaximumBoundary ) {

            throw new RuntimeException("Line start positions must be within the bounds of the drawable area in the canvas.");
        }

        if (x1 != x2 && y1 != y2) {

            throw new RuntimeException("Line start and end positions must be on the same orthogonal axis.");
        }

        Point[][] canvasData = canvas.getCanvasData();

        // This only works because the line must be on an orthogonal axis. If diagonal line support is added in the future
        // this will no longer work.
        int xStartPosition = Math.min(x1, x2);
        int xEndPosition = Math.max(x1, x2);

        int yStartPosition = Math.min(y1, y2);
        int yEndPosition = Math.max(y1, y2);

        // Add the line to the canvas.
        for (int x = xStartPosition; x <= xEndPosition; x++) {

            for (int y = yStartPosition; y <= yEndPosition; y++) {

                canvasData[x][y] = new Point('x');
            }
        }

        return new Canvas(canvasData);
    }
}
