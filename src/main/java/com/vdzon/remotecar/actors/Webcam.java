package com.vdzon.remotecar.actors;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import com.vdzon.remotecar.Const;
import com.vdzon.remotecar.data.Point2D;
import com.vdzon.remotecar.messages.Img;
import com.vdzon.remotecar.messages.Playground;
import com.vdzon.remotecar.messages.RequestUpdate;
import com.vdzon.remotecar.util.GameUtils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.highgui.VideoCapture;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

/**
 * Created by robbert on 3-6-2015.
 */
public class Webcam extends UntypedActor {

    private ActorRef player;
    private BufferedImage image;
    private VideoCapture camera;
    private Mat frame;
    private boolean initialized = false;

    public Webcam(final ActorRef player) {
        this.player = player;
    }

    public void onReceive(Object message) {
        if (message instanceof RequestUpdate) {
            updatePlayer(getWebcamImage());
        } else {
            unhandled(message);
        }
    }

    private void updatePlayer(BufferedImage image) {
        initialize();
        player.tell(new Img(image), getSelf());
    }

    private BufferedImage getWebcamImage() {
        camera.retrieve(frame);
        image = matToBufferedImage(frame);
        return image;
    }

    private void initialize() {
        if (initialized) return;
        initialized = true;
        // for webcam
        camera = new VideoCapture(1);
        camera.open(1); //Useless
        if (!camera.isOpened()) {
            System.out.println("Camera Error");
        } else {
            System.out.println("Camera OK?");
        }
        frame = new Mat(600, 600, CvType.CV_8U, new Scalar(4));

    }


    public static BufferedImage matToBufferedImage(Mat matrix) {
        if (matrix.channels() == 1) {
            int cols = matrix.cols();
            int rows = matrix.rows();
            int elemSize = (int) matrix.elemSize();
            byte[] data = new byte[cols * rows * elemSize];
            int type;
            matrix.get(0, 0, data);
            switch (matrix.channels()) {
                case 1:
                    type = BufferedImage.TYPE_BYTE_GRAY;
                    break;
                case 3:
                    type = BufferedImage.TYPE_3BYTE_BGR;
                    // bgr to rgb
                    byte b;
                    for (int i = 0; i < data.length; i = i + 3) {
                        b = data[i];
                        data[i] = data[i + 2];
                        data[i + 2] = b;
                    }
                    break;
                default:
                    return null;
            }

            BufferedImage image2 = new BufferedImage(cols, rows, type);
            image2.getRaster().setDataElements(0, 0, cols, rows, data);
            return image2;
        }

        if (matrix.channels() == 3) {
            int width = matrix.width(), height = matrix.height(), channels = matrix.channels();
            byte[] sourcePixels = new byte[width * height * channels];
            matrix.get(0, 0, sourcePixels);
            // create new image and get reference to backing data
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
            final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
            System.arraycopy(sourcePixels, 0, targetPixels, 0, sourcePixels.length);
            return image;
        }

        return null;
    }

}
