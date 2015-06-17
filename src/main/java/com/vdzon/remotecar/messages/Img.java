package com.vdzon.remotecar.messages;

import java.awt.image.BufferedImage;

/**
 * Created by robbert on 3-6-2015.
 */
public class Img {
    private BufferedImage image;

    public Img(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }
}
