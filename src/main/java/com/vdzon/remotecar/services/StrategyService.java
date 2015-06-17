package com.vdzon.remotecar.services;

import com.vdzon.remotecar.Const;
import com.vdzon.remotecar.data.*;
import com.vdzon.remotecar.data.Strategy;
import com.vdzon.remotecar.util.GameUtils;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by robbert on 12-6-2015.
 */
public class StrategyService {

    private SegmentService segmentService = new SegmentService();
    private PathService pathService = new PathService();
    private Random random = new Random(0);

    public Strategy buildStrategy(Point2D carCenter,Point2D carEdge,Point2D ballEdge,Point2D ballCenter){
        Strategy bestStrategy = null;
        double bestScore = 0;

        // return null when we are already at the ball
        if (GameUtils.getDistance(carCenter, ballCenter)<10) {
            return new Strategy(new Path(new Point2D[0]),new Segments(new ArrayList<RouteSegment>()));
        }

        // check short path
        Path path = pathService.buildPath(carCenter,carEdge,ballEdge,ballCenter);
        Segments segments = segmentService.buildSegments(path);
        if (segmentService.validSegments(segments)){
            bestStrategy = new Strategy(path,segments);
            bestScore = segmentService.getScore(bestStrategy.getSegments());
        }

        // try with one extra point
        int maxTries = 1000;
        int tries = 0;
        while (tries++<maxTries){
            path = pathService.buildPath(carCenter,carEdge,getRandomPoint(Const.WIDTH, Const.HEIGHT),ballEdge,ballCenter);
            segments = segmentService.buildSegments(path);
            if (segmentService.validSegments(segments)){
                Strategy strategy = new Strategy(path,segments);
                double thisScore = segmentService.getScore(strategy.getSegments());
                if (thisScore>bestScore){
                    bestStrategy = strategy;
                    bestScore = thisScore;
                }
            }
        }

        // try with two extra points
        maxTries = 1000;
        tries = 0;
        while (tries++<maxTries ){
            path = pathService.buildPath(carCenter,carEdge,getRandomPoint(Const.WIDTH, Const.HEIGHT),getRandomPoint(Const.WIDTH, Const.HEIGHT),ballEdge,ballCenter);
            segments = segmentService.buildSegments(path);
            if (segmentService.validSegments(segments)){
                Strategy strategy = new Strategy(path,segments);
                double thisScore = segmentService.getScore(strategy.getSegments());
                if (thisScore>bestScore){
                    bestStrategy = strategy;
                    bestScore = thisScore;
                }
            }
        }
        return bestStrategy;
    }

    private Point2D getRandomPoint(int maxWidth, int maxHeight){
        return new Point2D(random.nextDouble()*maxWidth,random.nextDouble()*maxHeight);
    }

}
