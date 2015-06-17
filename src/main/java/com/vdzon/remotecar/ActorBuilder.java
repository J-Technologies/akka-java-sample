package com.vdzon.remotecar;

import akka.actor.*;
import akka.util.Duration;
import com.vdzon.remotecar.actors.*;
import com.vdzon.remotecar.messages.RequestUpdate;
import com.vdzon.remotecar.visuals.VisualForm;
import scala.actors.threadpool.Arrays;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by robbert on 3-6-2015.
 */
public class ActorBuilder {


    public Props getWebcamSimulator(final ActorRef player){
        return new Props(new UntypedActorFactory() {
            public UntypedActor create() {
                return new WebcamSimulator(player);
            }
        });
    }

    public Props getPlaygroundSimulator(final ActorRef webcamSimulator){
        return new Props(new UntypedActorFactory() {
            public UntypedActor create() {
                return new PlaygroundSimulator(webcamSimulator);
            }
        });
    }


    public Props getVisual(final VisualForm visualForm){
        return new Props(new UntypedActorFactory() {
            public UntypedActor create() {
                return new Visual(visualForm);
            }
        });
    }

    public Props getSimulatedBall(){
        return new Props(new UntypedActorFactory() {
            public UntypedActor create() {
                return new SimulatedBall();
            }
        });
    }

    public Props getSimulatedCar(){
        return new Props(new UntypedActorFactory() {
            public UntypedActor create() {
                return new SimulatedCar();
            }
        });
    }

    public Props getRemoteCar(){
        return new Props(new UntypedActorFactory() {
            public UntypedActor create() {
                return new RemoteCar();
            }
        });
    }

    public Props getRouteplanner(){
        return new Props(new UntypedActorFactory() {
            public UntypedActor create() {
                return new Routeplanner();
            }
        });
    }


    public List<ActorRef> getCarControlListeners(ActorRef... listeners){
        return Arrays.asList(listeners);
    }

}
