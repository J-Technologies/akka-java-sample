package com.vdzon.remotecar.messages;

import com.vdzon.remotecar.data.Coordinates;

/**
 * Created by robbert on 17-6-2015.
 */
public class CoordinatesResponse {
    private Coordinates coordinates;
    private String ref;

    public CoordinatesResponse(Coordinates coordinates, String ref) {
        this.coordinates = coordinates;
        this.ref = ref;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public String getRef() {
        return ref;
    }

    @Override
    public String toString() {
        return "CoordinatesResponse{" +
                "coordinates=" + coordinates +
                ", ref='" + ref + '\'' +
                '}';
    }
}
