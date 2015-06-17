package com.vdzon.remotecar.messages;

import com.vdzon.remotecar.data.RouteSegment;

import java.util.List;

/**
 * Created by robbert on 17-6-2015.
 */
public class RoutePlan {
    private List<RouteSegment> route;

    public RoutePlan(List<RouteSegment> route) {
        this.route = route;
    }

    public List<RouteSegment> getRoute() {
        return route;
    }

}
