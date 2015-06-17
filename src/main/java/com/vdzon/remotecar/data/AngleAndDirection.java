package com.vdzon.remotecar.data;

/**
 * Created by robbert on 13-6-2015.
 */
public class AngleAndDirection {
    public Direction direction = null;
    public double angle = 0;

    public AngleAndDirection(Direction direction, double angle) {
        this.direction = direction;
        this.angle = angle;
    }
}
