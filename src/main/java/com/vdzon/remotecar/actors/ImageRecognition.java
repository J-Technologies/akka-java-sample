package com.vdzon.remotecar.actors;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import com.vdzon.remotecar.Const;
import com.vdzon.remotecar.data.Point2D;
import com.vdzon.remotecar.messages.Img;
import com.vdzon.remotecar.messages.RecognizeRequest;
import com.vdzon.remotecar.messages.RecognizeResponse;

/**
 * Created by robbert on 3-6-2015.
 */
public class ImageRecognition extends UntypedActor {
    public void onReceive(Object message) {
        if (message instanceof RecognizeRequest) {
            recognizeObject((RecognizeRequest) message);
        } else {
            unhandled(message);
        }
    }

    private void recognizeObject(RecognizeRequest recognizeRequest){

        double carAngle = SimulatedCar.tempPublicAngle;
        Point2D carPos = SimulatedCar.tempPublicPos;
        Point2D ballPos = Const.BALL_START_POS;

        getSender().tell(new RecognizeResponse(carPos,ballPos,carAngle), getSelf());
    }
}
