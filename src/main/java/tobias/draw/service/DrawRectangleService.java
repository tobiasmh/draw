package tobias.draw.service;

import tobias.draw.model.Canvas;
import tobias.draw.model.Point;

/**
 * Created by tobias on 11/12/16.
 */
public class DrawRectangleService implements IDrawRectangleService {

    @Override
    public Canvas drawRectangle(Canvas canvas, int x1, int y1, int x2, int y2) {

        int widthMaximumBoundary = canvas.getWidth() - 2;
        int heightMaximumBoundary = canvas.getHeight() - 2;

        int borderSize = 1;
        if (x1 < borderSize || x1 > widthMaximumBoundary || y1 < borderSize || y1 > heightMaximumBoundary
                || x2 < borderSize || x2 > widthMaximumBoundary || y2 < borderSize || y2 > heightMaximumBoundary ) {

            throw new RuntimeException("Recentage positions must be within the bounds of the drawable area in the canvas.");
        }

        if (x2 < x1 || y2 < y1) {

            throw new RuntimeException("Rectangles positions must be provided in top left / bottom right format.");
        }

        Point[][] canvasData = canvas.getCanvasData();

        // Add the top and bottom walls of the rectangle
        for (int x = x1; x <= x2; x++) {

            canvasData[x][y1] = new Point('x');
            canvasData[x][y2] = new Point('x');

        }

        // Add the left and right walls of the rectangle
        for (int y = y1 + borderSize; y <= y2 - borderSize; y++) {

            canvasData[x1][y] = new Point('x');
            canvasData[x2][y] = new Point('x');
        }

        return new Canvas(canvasData);
    }
}
