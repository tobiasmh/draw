package tobias.draw.model;

/**
 * Created by Tobias on 10/12/16.
 *
 * A special case of the Point for a fill. Its special because when a fill takes place and finds an existing fill point
 * it needs to be able to determine that it is a fill and not a protected line, rectangle or border so that it can be
 * overwritten.
 */
public class FillPoint extends Point {

    public FillPoint(char representation) {
        super(representation);
    }

}
