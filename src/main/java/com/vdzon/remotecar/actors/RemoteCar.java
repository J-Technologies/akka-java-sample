package com.vdzon.remotecar.actors;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import com.vdzon.remotecar.messages.Img;
import com.vdzon.remotecar.messages.Playground;
import com.vdzon.remotecar.messages.RoutePlan;

/**
 * Created by robbert on 3-6-2015.
 */
public class RemoteCar extends UntypedActor {

    public void onReceive(Object message) {
        if (message instanceof RoutePlan) {
            controlCar((RoutePlan) message);
        } else {
            unhandled(message);
        }
    }

    private void controlCar(RoutePlan routePlan){
        // send control to remote car
    }

}
