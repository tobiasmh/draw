package tobias.draw.model;

/**
 * Created by Tobias on 10/12/16.
 *
 * A wrapper class provided for future extensibility. It could hold attributes like color that could not fit in a char.
 */
public class Point {

    private char representation;
    char getRepresentation() { return this.representation; }

    public Point(char representation) {

        this.representation = representation;
    }

    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof Point)) {
            return false;
        }

        Point p = (Point) obj;
        return p.representation == this.representation;

    }

    @Override
    public String toString() {
        return Character.toString(this.representation);
    }
}
