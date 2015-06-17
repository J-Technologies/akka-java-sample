package com.vdzon.remotecar.messages;

/**
 * Created by robbert on 3-6-2015.
 */
public class RecognizeRequest {
    private Img img;

    public RecognizeRequest(Img img) {
        this.img = img;
    }

    public Img getImg() {
        return img;
    }

    public void setImg(Img img) {
        this.img = img;
    }
}
