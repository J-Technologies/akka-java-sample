package com.vdzon.remotecar.actors;

import akka.actor.UntypedActor;
import com.vdzon.remotecar.Const;
import com.vdzon.remotecar.data.Point2D;
import com.vdzon.remotecar.data.Strategy;
import com.vdzon.remotecar.exceptions.RouteplanException;
import com.vdzon.remotecar.messages.*;
import com.vdzon.remotecar.services.StrategyService;
import com.vdzon.remotecar.util.GameUtils;

/**
 * Created by robbert on 3-6-2015.
 */
public class Routeplanner extends UntypedActor {
    private StrategyService strategyService = new StrategyService();
    private int failureCountdown = 10;

    public Routeplanner() {
        System.out.println("BUILDING NEW ROUTEPLANNER");
    }

    public void onReceive(Object message) {
        if (message instanceof RouteplanRequest) {
            calculateStrategy((RouteplanRequest) message);
        }
        else{
            unhandled(message);
        }
    }

    @Override
    public void preRestart(java.lang.Throwable reason, scala.Option<java.lang.Object> message) {
        System.out.println("PRE-RESTART");
    }

    @Override
    public void postRestart(Throwable reason) {
        System.out.println("POST-RESTART");
    }

    private void calculateStrategy(RouteplanRequest routeplanRequest){
        if (failureCountdown--==0){
            throw new RouteplanException();
        }
        double angleBall = routeplanRequest.getBallAngle();
        Point2D ballCenter = routeplanRequest.getBallPos();
        Point2D ballEdge = GameUtils.getNextPoint(ballCenter, angleBall, Const.BALL_SIZE);

        double angleCar = routeplanRequest.getCarAngle();
        Point2D carCenter = routeplanRequest.getCarPos();
        Point2D carEdge = GameUtils.getNextPoint(carCenter, angleCar, Const.BALL_SIZE);

        Strategy strategy = strategyService.buildStrategy(carCenter, carEdge, ballEdge, ballCenter);
        RoutePlan routePlan = new RoutePlan(strategy.getSegments().getSegments());

        getSender().tell(routePlan, getSelf());
    }



}
