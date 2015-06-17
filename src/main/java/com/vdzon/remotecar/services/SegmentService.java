package com.vdzon.remotecar.services;

import com.vdzon.remotecar.Const;
import com.vdzon.remotecar.data.*;
import com.vdzon.remotecar.util.GameUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by robbert on 12-6-2015.
 */
public class SegmentService {

    public Segments buildSegments(Path path){
        Point2D[] splittedPath = splitSections(path.getPath());
        Segments segments = calculateSegments(splittedPath);
        return segments;
    }

    public double getScore(Segments segments){
        double length = getSegmentsLength(segments);
        double minRadius = getMinRadius(segments);
        double boetefactor = 1;
        if (minRadius<5){
            boetefactor = 5;
        }
        else if (minRadius<10){
            boetefactor = 5;
        }
        else if (minRadius<20){
            boetefactor = 3;
        }
        else if (minRadius<30){
            boetefactor = 2;
        }
        return 1000000/(length*boetefactor);
    }


    public double getSegmentsLength(Segments segments){
        double length = 0;
        for (RouteSegment s:segments.getSegments()){
            length += s.getLength();
        }
        return length;
    }

    public double getMinRadius(Segments segments){
        double minRadius = Double.MAX_VALUE;
        for (RouteSegment s:segments.getSegments()){
            if (s instanceof ArcSegment) {
                ArcSegment arcSegment = (ArcSegment)s;
                if (arcSegment.getRadius() < minRadius) {
                    minRadius = arcSegment.getRadius();
                }
            }
        }
        return minRadius;
    }

    public boolean validSegments(Segments segments){
        return getMinRadius(segments)> Const.MIN_RADIUS;
    }

    protected Point2D[] splitSections(Point2D[] path){
        // split alle tussenliggende segmenten in twee delen (de eerste en laatse dus niet)
        int index = 0;
        int resultIndex = 0;
        Point2D[] result = new Point2D[2+2*(path.length-2)];

        while (index<path.length){
            result[resultIndex] = path[index];
            if (index!=0 && index<(path.length-2)){ // yes: -2, we skip the last segment
                // add point between this point and the next
                resultIndex++;
                result[resultIndex] = GameUtils.inBetween(path[index], path[index + 1]);
            }
            resultIndex++;
            index++;
        }
        return result;
    }


    protected Segments calculateSegments(Point2D[] path){
        List<RouteSegment> segments = new ArrayList<RouteSegment>();
        int index = 1;
        while (index<path.length-2){
            Point2D p0 = path[index-1];
            Point2D p1 = path[index];
            Point2D p2 = path[index+1];
            double l1 = GameUtils.getDistance(p0,p1);
            double l2 = GameUtils.getDistance(p1,p2);
            double aanliggendeZijde = l1<l2 ? l1 : l2; // kleinste zijde
            AngleAndDirection angleBetweenLines = GameUtils.getAngle(p0, p1, p2);
            double angleSchuineZijde = angleBetweenLines.angle/2;
            if (l1>aanliggendeZijde){
                // begin met rechte lijn
                double firstSegmentSize = l1-aanliggendeZijde;
                double lineAngle = GameUtils.calcAngle(p0,p1);
                Point2D newPoint = GameUtils.getNextPoint(p0, lineAngle, firstSegmentSize);
                segments.add(new StraightSegment(firstSegmentSize,p0,newPoint));
            }

            // bereken arc
            double lengthSchuineZijde = aanliggendeZijde / Math.cos(Math.toRadians(angleSchuineZijde));
            double lengteOverstaandeZijde = Math.sqrt(lengthSchuineZijde*lengthSchuineZijde-aanliggendeZijde*aanliggendeZijde);
            double radios = lengteOverstaandeZijde;
            double middelpuntshoek = 180-angleBetweenLines.angle;
            double lengteCircelboog = radios*Math.toRadians(middelpuntshoek);
            double firstLineAngle = GameUtils.calcAngle(p0,p1);
            double angleArcCenter = angleBetweenLines.direction==Direction.RIGHT ? 180+firstLineAngle+angleSchuineZijde : 180+firstLineAngle-angleSchuineZijde;
//            double secondLineAngle = GameUtils.calcAngle(p1,p2);
            Point2D circleCenter = GameUtils.getNextPoint(p1, angleArcCenter, lengthSchuineZijde);
            double startAngle = GameUtils.normalizeAngle(firstLineAngle)-middelpuntshoek;
            if (angleBetweenLines.direction==Direction.LEFT){
                startAngle = GameUtils.normalizeAngle(firstLineAngle)+middelpuntshoek;
            }

            segments.add(new ArcSegment(radios,lengteCircelboog,circleCenter,startAngle,middelpuntshoek,angleBetweenLines.direction));

            if (l2>aanliggendeZijde){
                // eindig met rechte lijn
                double secondSegmentSize = l2-aanliggendeZijde;
                double lineAngle = GameUtils.calcAngle(p1,p2);
                Point2D newPoint = GameUtils.getNextPoint(p1,lineAngle,aanliggendeZijde);
                segments.add(new StraightSegment(secondSegmentSize,newPoint,p2));
            }

            index+=2;// 2 points further
        }

        return new Segments(segments);
    }


}
