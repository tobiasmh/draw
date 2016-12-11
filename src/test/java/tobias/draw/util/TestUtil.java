package tobias.draw.util;

import tobias.draw.model.Point;

/**
 * Created by tobias on 10/12/16.
 */
public class TestUtil {

    // Use this to help make the tests more readable
    public static Point[][] createPointDatafromStringArray(String[] inputArray) {
        if (inputArray.length == 0) {
            return new Point[0][0];
        }
        Point[][] pointData = new Point[inputArray.length][inputArray[0].length()];

        for (int y = 0; y < inputArray.length; y++) {
            char[] s = inputArray[y].toCharArray();

            for (int x = 0; x < s.length; x++) {
                char c = s[x];

                if (c != ' ') {
                    pointData[x][y] = new Point(c);
                }

            }

        }
        return pointData;
    }

}
