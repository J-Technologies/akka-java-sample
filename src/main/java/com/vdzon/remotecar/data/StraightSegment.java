package com.vdzon.remotecar.data;

/**
 * Created by robbert on 12-6-2015.
 */
public class StraightSegment implements RouteSegment {
    private double length = 0;
    private Point2D startPoint;
    private Point2D endPoint;

    public StraightSegment(double length, Point2D startPoint, Point2D endPoint) {
        this.length = length;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public Point2D getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point2D startPoint) {
        this.startPoint = startPoint;
    }

    public Point2D getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point2D endPoint) {
        this.endPoint = endPoint;
    }

    @Override
    public String toString() {
        return "StraightSegment{" +
                "length=" + length +
                ", startPoint=" + startPoint +
                ", endPoint=" + endPoint +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StraightSegment that = (StraightSegment) o;

        if (Double.compare(that.length, length) != 0) return false;
        if (endPoint != null ? !endPoint.equals(that.endPoint) : that.endPoint != null) return false;
        if (startPoint != null ? !startPoint.equals(that.startPoint) : that.startPoint != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(length);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + (startPoint != null ? startPoint.hashCode() : 0);
        result = 31 * result + (endPoint != null ? endPoint.hashCode() : 0);
        return result;
    }
}
