package com.vdzon.remotecar.util;


import com.vdzon.remotecar.Const;
import com.vdzon.remotecar.data.AngleAndDirection;
import com.vdzon.remotecar.data.Direction;
import com.vdzon.remotecar.data.Point2D;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by robbert on 7-6-2015.
 */
public class GameUtils {

    public static Point2D getNextPoint(Point2D oldPoint, double angle, double length) {
        double x = (oldPoint.x + length * Math.sin(Math.toRadians(angle)));
        double y = (oldPoint.y + length * Math.cos(Math.toRadians(angle)));
        return new Point2D((float) x, (float) y);
    }


    public static double getDistance(Point2D p1, Point2D p2) {
        return Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2));
    }

    public static double calcAngle(Point2D p1, Point2D p2) {
        double a = Math.atan2(p2.getX() - p1.getX(), p2.getY() - p1.getY());
        a = Math.toDegrees(a);
        return a;
    }

    public static double normalizeAngle(double angle) {
        // create positive
        while (angle < 0) {
            angle += 360;
        }
        angle %= 360.0; // [0..360) if angle is positive, (-360..0] if negative
        return angle;
    }


    public static AngleAndDirection getAngle(Point2D p1, Point2D p2, Point2D p3) {
        double angle1 = calcAngle(p1, p2);
        double angle2 = calcAngle(p3, p2);
        double angle = normalizeAngle(angle2 - angle1);

        if (angle >= 180) {
            return new AngleAndDirection(Direction.LEFT, 360 - angle);
        }
        return new AngleAndDirection(Direction.RIGHT, angle);
    }

    public static Point2D inBetween(Point2D p1, Point2D p2) {
        return new Point2D(p1.x / 2 + p2.x / 2, p1.y / 2 + p2.y / 2);

    }


}
