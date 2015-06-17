package com.vdzon.remotecar;
import akka.actor.*;
import com.vdzon.remotecar.actors.*;
import akka.util.Duration;
import com.vdzon.remotecar.messages.RequestUpdate;
import com.vdzon.remotecar.visuals.VisualForm;
import scala.actors.threadpool.Arrays;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by robbert on 3-6-2015.
 */
public class Bootstrap {

    public static void main(String[] args){
//        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.start();
    }

    private void start(){
        ActorSystem system = ActorSystem.create("RemoteCarSystem");
        VisualForm visualForm = buildAndShowDialog();
        ActorRef player = system.actorOf(getPlayer(visualForm), "player");
        ActorRef webcamSimulator = system.actorOf(getWebcamSimulator(player), "webcamSimulator");
        ActorRef playgroundSimulator = system.actorOf(getPlaygroundSimulator(webcamSimulator), "playgroundSimulator");

        // schedular for update requests
        Duration duration = Duration.create(100, TimeUnit.MILLISECONDS);
        System.out.println(playgroundSimulator);
        system.scheduler().schedule(duration, duration, playgroundSimulator, new RequestUpdate());

    }

    private VisualForm buildAndShowDialog() {
        VisualForm visualForm = new VisualForm();
        visualForm.pack();
        visualForm.setVisible(true);
        return visualForm;
    }


    private Props getVisual(final VisualForm visualForm){
        return new Props(new UntypedActorFactory() {
            public UntypedActor create() {
                return new Visual(visualForm);
            }
        });
    }

    private Props getPlayer(final VisualForm visualForm){
        return new Props(new UntypedActorFactory() {
            public UntypedActor create() {
                return new Player(visualForm);
            }
        });
    }

    private Props getWebcamSimulator(final ActorRef player){
        return new Props(new UntypedActorFactory() {
            public UntypedActor create() {
                return new WebcamSimulator(player);
            }
        });
    }

    private Props getPlaygroundSimulator(final ActorRef webcamSimulator){
        return new Props(new UntypedActorFactory() {
            public UntypedActor create() {
                return new PlaygroundSimulator(webcamSimulator);
            }
        });
    }

    private Props getSimulatedBall(){
        return new Props(new UntypedActorFactory() {
            public UntypedActor create() {
                return new SimulatedBall();
            }
        });
    }

    private Props getSimulatedCar(){
        return new Props(new UntypedActorFactory() {
            public UntypedActor create() {
                return new SimulatedCar();
            }
        });
    }

    private Props getRemoteCar(){
        return new Props(new UntypedActorFactory() {
            public UntypedActor create() {
                return new RemoteCar();
            }
        });
    }

    private Props getRouteplanner(){
        return new Props(new UntypedActorFactory() {
            public UntypedActor create() {
                return new Routeplanner();
            }
        });
    }


    private List<ActorRef> getCarControlListeners(ActorRef... listeners){
        return Arrays.asList(listeners);
    }

}
