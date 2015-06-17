package com.vdzon.remotecar.messages;

import com.vdzon.remotecar.data.Coordinates;

/**
 * Created by robbert on 3-6-2015.
 */
public class Playground {
    private Coordinates ballCoordinates;
    private Coordinates carCoordinates;

    public Playground(Coordinates ballCoordinates, Coordinates carCoordinates) {
        this.ballCoordinates = ballCoordinates;
        this.carCoordinates = carCoordinates;
    }

    public Coordinates getBallCoordinates() {
        return ballCoordinates;
    }

    public Coordinates getCarCoordinates() {
        return carCoordinates;
    }
}
