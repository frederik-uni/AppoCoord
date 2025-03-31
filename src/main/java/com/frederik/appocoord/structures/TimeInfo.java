package com.frederik.appocoord.structures;

import java.io.Serializable;

public class TimeInfo implements Serializable  {
    private long start;
    private long end;

    public TimeInfo(long start, long end) {
        this.start = start;
        this.end = end;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }
}
