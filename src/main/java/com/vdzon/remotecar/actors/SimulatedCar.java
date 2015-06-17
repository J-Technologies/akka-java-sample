package com.vdzon.remotecar.actors;

import akka.actor.UntypedActor;
import com.vdzon.remotecar.Const;
import com.vdzon.remotecar.data.*;
import com.vdzon.remotecar.messages.*;
import com.vdzon.remotecar.util.GameUtils;

import java.util.Random;

/**
 * Created by robbert on 3-6-2015.
 */
public class SimulatedCar extends UntypedActor {

    public static Point2D tempPublicPos = Const.CAR_START_POS; // temporary hack, remove this when image recognition is finished
    public static double tempPublicAngle = 0; // temporary hack, remove this when image recognition is finished
    private Random random = new Random(System.currentTimeMillis());

    private Point2D pos = Const.CAR_START_POS;
    private double carAngle = Const.CAR_START_ANGLE;
    private double steerAngle = 0; // the angle it changes xx pixels
    private double speed = 10;
    private long beginTime = System.currentTimeMillis();
    ;
    private RoutePlan routePlan;
    private int currentRoutePlanStep = 0;

    public SimulatedCar() {
        startUpdateSimulatedPosThread();
        startUpdateSteerAndGasThread();
    }

    public void onReceive(Object message) {
        if (message instanceof CoordinatesRequest) {
            updatePosAndAngle((CoordinatesRequest) message);
        } else if (message instanceof RoutePlan) {
            controlCar((RoutePlan) message);
        } else if (message instanceof NewRandomPostitionRequest) {
            newRandomPosition();
        } else {
            unhandled(message);
        }
    }

    private void updatePosAndAngle(CoordinatesRequest coordinatesRequest) {
        getSender().tell(new CoordinatesResponse(new Coordinates(pos, carAngle), coordinatesRequest.getRef()), getSelf());

    }

    private void newRandomPosition() {
        pos = new Point2D(50 + random.nextDouble() * (Const.WIDTH - 100), 50 + random.nextDouble() * (Const.HEIGHT - 100));
        carAngle = random.nextDouble() * 360;
    }


    private void controlCar(RoutePlan routePlan) {
        this.routePlan = routePlan;
        currentRoutePlanStep = -1;
    }


    private void startUpdateSimulatedPosThread() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        long sleepTime = (long) (1000 / Const.NR_SIMULATIONS_UPDATE_CARPOS_PER_SECONDS);
                        Thread.sleep(sleepTime);
                        updateSimulatedPos();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    private void startUpdateSteerAndGasThread() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        updateSteerAndGas();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    public void updateSteerAndGas() throws InterruptedException {
        if (routePlan == null || routePlan.getRoute() == null) {
            stopCar();
            Thread.sleep(1);
            return;
        }
        currentRoutePlanStep++;
        if (currentRoutePlanStep < routePlan.getRoute().size()) {
            RouteSegment routeSegment = routePlan.getRoute().get(currentRoutePlanStep);
            if (routeSegment instanceof ArcSegment) {
                ArcSegment arcSegment = (ArcSegment) routeSegment;
                double lengthOfCircle = arcSegment.getRadius() * Math.toRadians(360);
                // angle is changes every Const.ANGLE_BASE_UNIT pixels
                // if radius=100, and length=500, anglebase=1000 then steerAngle = 720
                // 360*anglebase/length
                steerAngle = 360 * Const.ANGLE_BASE_UNIT / lengthOfCircle;
                if (arcSegment.getDirection() == Direction.RIGHT) {
                    steerAngle = steerAngle * -1;
                }
            }
            if (routeSegment instanceof StraightSegment) {
                StraightSegment straightSegment = (StraightSegment) routeSegment;
                steerAngle = 0;
            }
            speed = Const.CAR_SPEED;
            double delayInMsec = (routeSegment.getLength() / Const.CAR_SPEED) * 1000;
            long delayCount = 0;
            while (delayCount < (long) delayInMsec && currentRoutePlanStep != -1) {
                Thread.sleep(1);
                delayCount++;
            }
        } else {
            stopCar();
            Thread.sleep(1);
        }
    }

    public void updateSimulatedPos() {
        long time = System.currentTimeMillis();
        long timeDrivenInMsec = time - beginTime;
        double mSecPerSecond = 1000;
        double timeDrivenInSec = timeDrivenInMsec / mSecPerSecond;
        double movedDistance = speed * timeDrivenInSec;
        double movedAngle = steerAngle * movedDistance / Const.ANGLE_BASE_UNIT;
        double newCarAngle = carAngle + movedAngle;
        double averageCarAngleThisRun = (carAngle + newCarAngle) / 2;

        carAngle = newCarAngle;
        carAngle = GameUtils.normalizeAngle(carAngle);

        this.pos = GameUtils.getNextPoint(pos, averageCarAngleThisRun, movedDistance);
        tempPublicPos = this.pos;
        tempPublicAngle = carAngle;
        beginTime = System.currentTimeMillis();
    }

    private void stopCar() {
        steerAngle = 0;
//        speed = 0;
    }


}
