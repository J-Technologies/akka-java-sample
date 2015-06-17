package com.vdzon.remotecar.actors;

import akka.actor.UntypedActor;
import com.vdzon.remotecar.Const;
import com.vdzon.remotecar.data.Coordinates;
import com.vdzon.remotecar.data.Point2D;
import com.vdzon.remotecar.messages.CoordinatesRequest;
import com.vdzon.remotecar.messages.CoordinatesResponse;
import com.vdzon.remotecar.messages.NewRandomPostitionRequest;
import com.vdzon.remotecar.messages.RequestUpdate;

import java.util.Random;

/**
 * Created by robbert on 3-6-2015.
 */
public class SimulatedBall extends UntypedActor {

    private Point2D pos = Const.BALL_START_POS;
    private Random random = new Random(System.currentTimeMillis());

    public void onReceive(Object message) {
        if (message instanceof CoordinatesRequest) {
            updatePosAndAngle((CoordinatesRequest) message);
        } else if (message instanceof NewRandomPostitionRequest) {
            newRandomPosition();
        } else {
            unhandled(message);
        }
    }

    private void newRandomPosition() {
        pos = new Point2D(50+random.nextDouble()*(Const.WIDTH-100),50+random.nextDouble()*(Const.HEIGHT-100));
    }


    private void updatePosAndAngle(CoordinatesRequest coordinatesRequest) {
        getSender().tell(new CoordinatesResponse(new Coordinates(pos, 0),coordinatesRequest.getRef()), getSelf());
    }

}
