package com.vdzon.remotecar.data;

/**
 * Created by robbert on 12-6-2015.
 */
public class ArcSegment implements RouteSegment {

    private double radius = 0;
    private double length = 0;
    private Point2D centerPoint;
    private double startAngle = 0;
    private double middelpuntshoek = 0;
    private Direction direction = Direction.LEFT;


    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public Point2D getCenterPoint() {
        return centerPoint;
    }

    public void setCenterPoint(Point2D centerPoint) {
        this.centerPoint = centerPoint;
    }

    public double getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(double startAngle) {
        this.startAngle = startAngle;
    }

    public double getMiddelpuntshoek() {
        return middelpuntshoek;
    }

    public void setMiddelpuntshoek(double middelpuntshoek) {
        this.middelpuntshoek = middelpuntshoek;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public ArcSegment(double radius, double length, Point2D centerPoint, double startAngle, double middelpuntshoek, Direction direction) {
        this.radius = radius;
        this.length = length;
        this.centerPoint = centerPoint;
        this.startAngle = startAngle;
        this.middelpuntshoek = middelpuntshoek;
        this.direction = direction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArcSegment that = (ArcSegment) o;

        if (Double.compare(that.middelpuntshoek, middelpuntshoek) != 0) return false;
        if (Double.compare(that.length, length) != 0) return false;
        if (Double.compare(that.radius, radius) != 0) return false;
        if (Double.compare(that.startAngle, startAngle) != 0) return false;
        if (centerPoint != null ? !centerPoint.equals(that.centerPoint) : that.centerPoint != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(radius);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(length);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (centerPoint != null ? centerPoint.hashCode() : 0);
        temp = Double.doubleToLongBits(startAngle);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(middelpuntshoek);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "ArcSegment{" +
                "radius=" + radius +
                ", length=" + length +
                ", centerPoint=" + centerPoint +
                ", startAngle=" + startAngle +
                ", middelpuntshoek=" + middelpuntshoek +
                '}';
    }
}
