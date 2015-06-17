package com.vdzon.remotecar.data;

/**
 * Created by robbert on 12-6-2015.
 */
public class Path {
    private Point2D[] path = new Point2D[0];

    public Path(Point2D[] path) {
        this.path = path;
    }

    public Point2D[] getPath() {
        return path;
    }

    public void setPath(Point2D[] path) {
        this.path = path;
    }
}
