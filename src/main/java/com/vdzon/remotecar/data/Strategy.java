package com.vdzon.remotecar.data;

import com.vdzon.remotecar.data.Path;
import com.vdzon.remotecar.data.Segments;

/**
 * Created by robbert on 3-6-2015.
 */
public class Strategy {
    private Segments segments;
    private Path path;

    public Strategy(Path path, Segments segments) {
        this.segments = segments;
        this.path = path;
    }

    public Segments getSegments() {
        return segments;
    }

    public void setSegments(Segments segments) {
        this.segments = segments;
    }
}
