package tobias.draw.service;

import tobias.draw.model.Canvas;
import tobias.draw.model.Coordinate;
import tobias.draw.model.FillPoint;
import tobias.draw.model.Point;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Created by Tobias on 11/12/16.
 *
 */
public class FillCanvasService implements IFillCanvasService {

    /*
        The fill method uses a breadth first search of all neighbours of the selected point,
        and then the neighbours of the children sequentially. Once it reaches a point that is a protected point (line,
        rectangle or border) then it stops and continues with child inspections of any other viable points in the queue.
        When a viable points is reached it is populated with a fill point.
     */
    @Override
    public Canvas fill(Canvas canvas, int x, int y, char color) {

        if (x < 0 || x >= canvas.getWidth() || y < 0  || y >= canvas.getHeight()) {

            throw new RuntimeException("Fill point must be within the bounds of the canvas.");
        }

        Point[][] canvasData = canvas.getCanvasData();

        // To keep track of points already visited in order to not waste time revisiting them.
        boolean[][] visited = new boolean[canvas.getWidth()][canvas.getHeight()];

        // Children are appended to the end of this queue to facilitate the breadth first searching.
        Queue<Coordinate> pendingQueue = new ArrayDeque<Coordinate>();
        pendingQueue.add(new Coordinate(x, y));

        while (!pendingQueue.isEmpty()) {

            Coordinate currentCoordinate = pendingQueue.remove(); // Pops the top Point from the queue.

            int currentY = currentCoordinate.getY();
            int currentX = currentCoordinate.getX();

            Point currentPoint = canvasData[currentX][currentY];

            if (currentPoint == null // An empty point
                    || currentPoint instanceof FillPoint) { // Or the point is existing fill that can be overwritten

                canvasData[currentX][currentY] = new FillPoint(color); // The point is viable for fill, so fill is added.

                // Get the neighbour points: up, down, left, right
                // Only add unvisited children to the queue. This is important otherwise the number of possible children is exponential.
                if (currentY > 0) {

                    int neighbourY = currentY - 1; // Up
                    if (!visited[currentX][neighbourY]){

                        visited[currentX][neighbourY] = true;
                        pendingQueue.add(new Coordinate(currentX, neighbourY));
                    }
                }

                if (currentY < canvas.getWidth()) {

                    int neighbourY = currentY + 1; // Down
                    if (!visited[currentX][neighbourY]) {

                        visited[currentX][neighbourY] = true;
                        pendingQueue.add(new Coordinate(currentX, neighbourY));
                    }
                }

                if (currentX > 0) {

                    int neighbourX = currentX - 1; // Left
                    if (!visited[neighbourX][currentY]) {

                        visited[neighbourX][currentY] = true;
                        pendingQueue.add(new Coordinate(neighbourX, currentY));
                    }
                }

                if (currentY < canvas.getHeight()) {

                    int neighbourX = currentX + 1; // Right
                    if (!visited[neighbourX][currentY]) {

                        visited[neighbourX][currentY] = true;
                        pendingQueue.add(new Coordinate(neighbourX, currentY));
                    }
                }
            }
        }

        return new Canvas(canvasData);
    }
}
