package com.vdzon.remotecar.services;


import com.vdzon.remotecar.data.Path;
import com.vdzon.remotecar.data.Point2D;

/**
 * Created by robbert on 12-6-2015.
 */
public class PathService {

    public Path buildPath(Point2D... points){
        return new Path(points);
    }


}
