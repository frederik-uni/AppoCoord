package com.frederik.appocoord.models.parts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimeInfo {
    private long start;
    private long end;

    @JsonCreator
    public TimeInfo(@JsonProperty("start") long start, @JsonProperty("end") long end) {
        this.start = start;
        this.end = end;
    }
}
