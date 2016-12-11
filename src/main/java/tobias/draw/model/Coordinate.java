package tobias.draw.model;

/**
 * Created by Tobias on 10/12/16.
 *
 * A simple value class for holding a coordinate pair. Useful for keeping coordinates in collections, and simplifying the
 * communication of a coordinate.
 */
public class Coordinate {

    private int x;
    public int getX() { return this.x; }

    private int y;
    public int getY() { return this.y; }

    public Coordinate(int x, int y) {

        this.x = x;
        this.y = y;
    }

}
