package com.vdzon.remotecar.actors;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import com.vdzon.remotecar.Const;
import com.vdzon.remotecar.data.Point2D;
import com.vdzon.remotecar.messages.*;
import com.vdzon.remotecar.util.GameUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by robbert on 3-6-2015.
 */
public class WebcamSimulator extends UntypedActor {

    private ActorRef player;

    private BufferedImage webcamImage;
    private Graphics2D graphics;
    private BufferedImage ballImage;
    private BufferedImage carImage;

    public WebcamSimulator(final ActorRef player) {
        this.player = player;
        initImage();
    }

    public void onReceive(Object message) {
        if (message instanceof Playground) {
            updatePlayer((Playground) message);
        } else {
            unhandled(message);
        }
    }

    private void updatePlayer(Playground playground) {
        updateImage(playground);
        player.tell(new Img(webcamImage), getSelf());
    }

    private void initImage() {
        webcamImage = new BufferedImage(Const.WIDTH, Const.HEIGHT, BufferedImage.TYPE_BYTE_INDEXED);
        graphics = (Graphics2D) webcamImage.getGraphics();
        try {
            ballImage = ImageIO.read(new File("voetbal.png"));
            carImage = ImageIO.read(new File("auto2.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void drawBall(Graphics2D g2d, Point2D ballPos) {
        AffineTransform identity = new AffineTransform();
        AffineTransform trans = new AffineTransform();
        trans.setTransform(identity);
        trans.setToTranslation((int) ballPos.getX() - (ballImage.getWidth() / 2), (int) (ballPos.getY() - ballImage.getHeight() / 2));
        g2d.drawImage(ballImage, trans, null);

    }

    private void drawCar(Graphics2D g2d, Point2D carPos, double carAngle) {
        AffineTransform identity = new AffineTransform();
        AffineTransform trans = new AffineTransform();
        trans.setTransform(identity);

//        trans.setToTranslation((int) carPos.getX()+(carImage.getWidth()/2), (int) (carPos.getY()+carImage.getHeight()/2));
        trans.setToTranslation((int) carPos.getX() - (carImage.getWidth() / 2), (int) (carPos.getY() - carImage.getHeight() / 2));
        trans.rotate(Math.toRadians(-1 * (carAngle)), carImage.getWidth() / 2, carImage.getHeight() / 2);
        g2d.drawImage(carImage, trans, null);

        // draw car as stripe
        g2d.setColor(Color.RED);
        Point2D secondPoint = GameUtils.getNextPoint(carPos, carAngle, 4);
        g2d.drawLine((int) carPos.x, (int) carPos.y, (int) secondPoint.x, (int) secondPoint.y);


    }


    public void updateImage(Playground playground) {
        Point2D carPos = playground.getCarCoordinates().getPos();
        Point2D ballPos = playground.getBallCoordinates().getPos();
        double carAngle = playground.getCarCoordinates().getAngle();
        graphics.clearRect(0, 0, Const.WIDTH, Const.HEIGHT);
        drawBall(graphics, ballPos);
        drawCar(graphics, carPos, carAngle);
    }

}
