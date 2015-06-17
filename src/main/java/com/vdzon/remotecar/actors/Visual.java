package com.vdzon.remotecar.actors;

import akka.actor.UntypedActor;
import com.vdzon.remotecar.messages.Img;
import com.vdzon.remotecar.messages.RoutePlan;
import com.vdzon.remotecar.visuals.VisualForm;

/**
 * Created by robbert on 3-6-2015.
 */
public class Visual extends UntypedActor {

    private VisualForm visualForm;

    public Visual(final VisualForm visualForm) {
        this.visualForm = visualForm;
    }

    public void onReceive(Object message) {
        if (message instanceof Img) {
            paintImage((Img) message);
        } else if (message instanceof RoutePlan) {
            paintRoute((RoutePlan) message);
        } else {
            unhandled(message);
        }
    }


    private void paintImage(Img img) {
        visualForm.paintWebcamImage(img);
    }

    private void paintRoute(RoutePlan routePlan) {
        visualForm.updateRoutePlan(routePlan);
    }

}
