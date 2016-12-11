package tobias.draw.model;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by Tobias on 10/12/16.
 *
 */
public class PointTest {

    @Test
    public void Equals_TwoEqualPoints_PointsAreEqual() {

        Point pointA = new Point('X');
        Point pointB = new Point('X');

        assertThat(pointA.equals(pointB), is(true));
    }

    @Test
    public void Equals_TwoUnequalPoints_PointsAreNotEqual() {

        Point pointA = new Point('X');
        Point pointB = new Point('Y');

        assertThat(pointA.equals(pointB), is(false));
    }

    @Test
    public void Equals_CompareToObjectThatIsNotPoint_PointsAreNotEqual() {

        Point pointA = new Point('X');
        List<Object> listB = new LinkedList<Object>();

        assertThat(pointA.equals(listB), is(false));
    }

    @Test
    public void ToString_StringResult_ExpectedStringIsProvided() {

        Point point = new Point('A');

        assertThat(point.toString(), is(equalTo("A")));
    }

}
