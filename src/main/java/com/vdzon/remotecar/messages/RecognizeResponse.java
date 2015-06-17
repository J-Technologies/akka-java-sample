package com.vdzon.remotecar.messages;

import com.vdzon.remotecar.data.Point2D;

/**
 * Created by robbert on 3-6-2015.
 */
public class RecognizeResponse {
    Point2D carPos;
    Point2D ballPos;
    double carAngle;

    public RecognizeResponse(Point2D carPos, Point2D ballPos, double carAngle) {
        this.carPos = carPos;
        this.ballPos = ballPos;
        this.carAngle = carAngle;
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
}
