package com.frederik.appocoord.models.parts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class TimeInfo implements Serializable {
    private long start;
    private long end;

    @JsonCreator
    public TimeInfo(@JsonProperty("start") long start, @JsonProperty("end") long end) {
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
