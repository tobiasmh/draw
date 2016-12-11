package tobias.draw.model;

/**
 * Created by Tobias on 10/12/16.
 *
 * This model object is designed for future extensibility and could provide features like holding a history of drawing steps.
 */
public class Canvas {


    private int width;

    private int height;

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    private Point[][] canvasData;
    public Point[][] getCanvasData() { return this.canvasData; }

    public Canvas(Point[][] canvasData) {

        this.canvasData = canvasData;
        this.width = canvasData.length;
        this.height = 0;

        if (this.width > 0) {

            this.height = canvasData[0].length;
        }
    }

    public String[] getPrintRepresentation() {

        String[] output = new String[this.getHeight()];

        Point[][] canvasData = this.getCanvasData();

        for (int y = 0; y < this.getHeight(); y++) {

            StringBuilder rowStringBuilder = new StringBuilder();

            for (int x = 0; x < this.getWidth(); x++) {

                Point[] pointColumn = canvasData[x];

                Point point = pointColumn[y];
                if (point == null) {

                    rowStringBuilder.append(" ");

                } else {

                    rowStringBuilder.append(point.getRepresentation());
                }
            }
            output[y] = rowStringBuilder.toString();
        }
        return output;
    }

}
