package com.vdzon.remotecar.data;

import java.util.List;

/**
 * Created by robbert on 12-6-2015.
 */
public class Segments {
    public List<RouteSegment> segments;

    public Segments(List<RouteSegment> segments) {
        this.segments = segments;
    }

    public List<RouteSegment> getSegments() {
        return segments;
    }

    public void setSegments(List<RouteSegment> segments) {
        this.segments = segments;
    }
}
