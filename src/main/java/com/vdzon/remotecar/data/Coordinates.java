package com.vdzon.remotecar.data;

import com.vdzon.remotecar.data.Point2D;

/**
 * Created by robbert on 3-6-2015.
 */
public class Coordinates {
    private Point2D pos;
    private double angle;

    public Coordinates(Point2D pos, double angle) {
        this.pos = pos;
        this.angle = angle;
    }

    public Point2D getPos() {
        return pos;
    }

    public double getAngle() {
        return angle;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "pos=" + pos +
                ", angle=" + angle +
                '}';
    }
}
