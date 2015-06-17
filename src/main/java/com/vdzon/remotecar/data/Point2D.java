package com.vdzon.remotecar.data;

public class Point2D {
    public double x, y;

    public Point2D() {
        this(0f, 0f);
    }

    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return the x
     */
    public double getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public double getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Point2D(" + x +","+y+")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point2D point2D = (Point2D) o;

        if (Double.compare(point2D.x, x) != 0) return false;
        if (Double.compare(point2D.y, y) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        long result = (x != +0.0f ? Double.doubleToLongBits(x) : 0);
        result = 31 * result + (y != +0.0f ? Double.doubleToLongBits(y) : 0);
        return (int)result;
    }
}