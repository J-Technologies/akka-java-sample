package com.vdzon.remotecar.actors;

import akka.actor.*;
import akka.japi.Function;
import akka.util.Duration;
import com.vdzon.remotecar.ActorBuilder;
import com.vdzon.remotecar.exceptions.RouteplanException;
import com.vdzon.remotecar.messages.*;
import com.vdzon.remotecar.util.GameUtils;
import com.vdzon.remotecar.visuals.VisualForm;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static akka.actor.SupervisorStrategy.*;

/**
 * Created by robbert on 3-6-2015.
 */
public class Player extends UntypedActor {

    private ActorRef imageRecognition;
    private ActorRef visual;
    private ActorRef routeplanner;
    private int count = 0;
    private ActorBuilder actorBuilder = new ActorBuilder();

    public Player(final VisualForm visualForm) {
        System.out.println("BUILDING NEW PLAYER");
        this.visual = getContext().actorOf(actorBuilder.getVisual(visualForm), "visual");
        this.imageRecognition = getContext().actorOf(new Props(ImageRecognition.class), "imageRecognition");
        this.routeplanner = getContext().actorOf(actorBuilder.getRouteplanner(), "routeplanner");
    }

    private static SupervisorStrategy strategy =
            new OneForOneStrategy(10, Duration.create(2, TimeUnit.SECONDS),
                    new Function<Throwable, SupervisorStrategy.Directive>() {
                        @Override
                        public SupervisorStrategy.Directive apply(Throwable t) {
                            if (t instanceof RouteplanException) {
                                return restart();
//                                return stop();
//                                return resume();
                            } else {
                                return escalate();
                            }
                        }
                    });

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }


    public void onReceive(Object message) {
        if (message instanceof Img) {
            findComponentPositions((Img) message);
        } else if (message instanceof RecognizeResponse) {
            getRoute((RecognizeResponse) message);
        } else if (message instanceof RoutePlan) {
            distributeControl((RoutePlan) message);
        } else {
            unhandled(message);
        }
    }

    private void findComponentPositions(Img img) {
        visual.tell(img, getSelf());
        imageRecognition.tell(new RecognizeRequest(img), getSelf());
    }

    private void getRoute(RecognizeResponse recognizeResponse) {
        if (collisionDetected(recognizeResponse)) {
            ActorRef simulatedCar = getContext().actorFor("akka://RemoteCarSystem/user/playgroundSimulator/simulatedCar");
            ActorRef remoteCar = getContext().actorFor("akka://RemoteCarSystem/user/playgroundSimulator/remoteCar");
            simulatedCar.tell(new NewRandomPostitionRequest(), getSelf());
            remoteCar.tell(new NewRandomPostitionRequest(), getSelf());
        }
        if (count++ % 10 == 0) {
            routeplanner.tell(new RouteplanRequest(recognizeResponse.getCarPos(), recognizeResponse.getBallPos(), recognizeResponse.getCarAngle(), 0), getSelf());
        }
    }

    private boolean collisionDetected(RecognizeResponse recognizeResponse) {
        double distance = GameUtils.getDistance(recognizeResponse.getCarPos(), recognizeResponse.getBallPos());
        count = 0;
        return distance < 30;
    }


    private void distributeControl(RoutePlan routePlan) {
        visual.tell(routePlan, getSelf());
        ActorRef simulatedCar = getContext().actorFor("akka://RemoteCarSystem/user/playgroundSimulator/simulatedCar");
        ActorRef remoteCar = getContext().actorFor("akka://RemoteCarSystem/user/playgroundSimulator/remoteCar");
        simulatedCar.tell(routePlan, getSelf());
        remoteCar.tell(routePlan, getSelf());
    }

}
