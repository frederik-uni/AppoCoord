package com.frederik.appocoord.models.parts;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

import java.io.Serializable;

@Getter
@Setter
public class PollInfo implements Serializable {
    @NonNull
    protected String title;
    protected String description;
    protected String location;
    @NonNull
    protected int end;

    public PollInfo(@NonNull String title, String description, String location, int end) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.end = end;
    }
}
