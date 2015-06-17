package com.vdzon.remotecar.actors;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.japi.Procedure;
import com.vdzon.remotecar.ActorBuilder;
import com.vdzon.remotecar.data.Coordinates;
import com.vdzon.remotecar.messages.*;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

/**
 * Created by robbert on 3-6-2015.
 */
public class PlaygroundSimulator extends UntypedActor {

    private ActorRef simulatedCar;
    private ActorRef simulatedBall;
    private ActorRef webcamSimulator;
    private Coordinates recievedBallCoordinates;
    private Coordinates recievedCarCoordinates;
    private ActorBuilder actorBuilder = new ActorBuilder();

    public PlaygroundSimulator(final ActorRef webcamSimulator) {
        this.simulatedBall = getContext().actorOf(actorBuilder.getSimulatedBall(), "simulatedBall");
        this.simulatedCar = getContext().actorOf(actorBuilder.getSimulatedCar(), "simulatedCar");
        this.webcamSimulator = webcamSimulator;
    }

    Procedure<Object> waitingForCoordinates = new Procedure<Object>() {
        @Override
        public void apply(Object message) {
            if (message instanceof CoordinatesResponse) {
                updateCoordinates((CoordinatesResponse) message);
            } else {
                unhandled(message);
            }
        }
    };


    public void onReceive(Object message) {
        if (message instanceof RequestUpdate) {
            requestCoordinates();
        } else {
            unhandled(message);
        }
    }

    private void requestCoordinates() {
        recievedBallCoordinates = null;
        recievedCarCoordinates = null;
        simulatedCar.tell(new CoordinatesRequest("car"), getSelf());
        simulatedBall.tell(new CoordinatesRequest("ball"), getSelf());
        getContext().become(waitingForCoordinates);
    }

    private void updateCoordinates(CoordinatesResponse coordinatesResponse) {
        if (coordinatesResponse.getRef().equals("car")) {
            recievedCarCoordinates = coordinatesResponse.getCoordinates();
        }
        if (coordinatesResponse.getRef().equals("ball")) {
            recievedBallCoordinates = coordinatesResponse.getCoordinates();
        }
        if (recievedCarCoordinates != null && recievedBallCoordinates != null) {
            // all coordinates are recieved, send them to the webcam simulater and unbecome
            webcamSimulator.tell(new Playground(recievedBallCoordinates, recievedCarCoordinates), getSelf());
            getContext().unbecome();
        }
    }

}
