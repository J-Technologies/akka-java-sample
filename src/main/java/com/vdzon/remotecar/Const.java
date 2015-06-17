package com.vdzon.remotecar;


import com.vdzon.remotecar.data.Point2D;

/**
 * Created by robbert on 1-6-2015.
 */
public class Const {
    public static int WIDTH = 640;
    public static int HEIGHT = 480;

    public static double NR_SIMULATIONS_UPDATE_CARPOS_PER_SECONDS = 1000;

    public static double CAR_SPEED = 70; // pixels per seconde
    public static double ANGLE_BASE_UNIT = 1000; // when steer angle is X, then the car angle is change X degrees when this distance is driven

    public static int CAR_START_ANGLE = 180;
    public static Point2D CAR_START_POS = new Point2D(50,50);
    public static Point2D BALL_START_POS = new Point2D(300,300);

    public static int MIN_RADIUS = 10;
    public static int BALL_SIZE = 10;

}
