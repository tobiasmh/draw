package tobias.draw.service;

import tobias.draw.model.Canvas;
import tobias.draw.model.Point;

/**
 * Created by Tobias on 11/12/16.
 *
 */
public class CreateCanvasService implements ICreateCanvasService {

    @Override
    public Canvas createCanvas(int width, int height) {

        if (width <= 0 || height <= 0) {
            throw new RuntimeException("Canvas Height and Width must both be greater than 0");
        }

        // The example provided indicated that the height does not include the top or bottom border size.
        // This extra height is appended here.
        height = height + 2;

        Point[][] canvasData = new Point[width][height];

        // The top and bottom borders.
        for (int x = 0; x < width; x++) {

            canvasData[x][0] = new Point('-');
            canvasData[x][height - 1] = new Point('-');
        }

        // The left and right borders.
        for (int y = 1; y < height - 1; y++) {

            canvasData[0][y] = new Point('|');
            canvasData[width - 1][y] = new Point('|');
        }

        return new Canvas(canvasData);
    }

}
