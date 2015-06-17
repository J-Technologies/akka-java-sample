package com.vdzon.remotecar.messages;

import com.vdzon.remotecar.data.Coordinates;

/**
 * Created by robbert on 17-6-2015.
 */
public class CoordinatesRequest {
    private String ref;

    public CoordinatesRequest(String ref) {
        this.ref = ref;
    }

    public String getRef() {
        return ref;
    }
}
