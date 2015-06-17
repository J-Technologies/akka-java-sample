package com.vdzon.remotecar.messages;

import com.vdzon.remotecar.data.Point2D;

/**
 * Created by robbert on 3-6-2015.
 */
public class RouteplanRequest {
    Point2D carPos;
    Point2D ballPos;
    double carAngle;
    double ballAngle;

    public RouteplanRequest(Point2D carPos, Point2D ballPos, double carAngle, double ballAngle) {
        this.carPos = carPos;
        this.ballPos = ballPos;
        this.carAngle = carAngle;
        this.ballAngle = ballAngle;
    }

    public Point2D getCarPos() {
        return carPos;
    }

    public Point2D getBallPos() {
        return ballPos;
    }

    public double getCarAngle() {
        return carAngle;
    }

    public double getBallAngle() {
        return ballAngle;
    }
}
