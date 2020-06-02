package model.util;

/** Simple class to represent 2D integer coordinates.
 * */
public class Point implements java.io.Serializable{
    /** The X coordinate of the point
     * */
    private int X;
    /** The Y coordinate of the point
     * */
    private int Y;

    /** The X and Y coordinates must be given to create a Point.
     * @param x The first coordinate of the point
     * @param y The second coordinate of the point
     * */
    public Point(int x, int y) {
        X = x;
        Y = y;
    }


    /** Getter and setter methods.
     * */

    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }
}
