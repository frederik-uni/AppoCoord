package com.frederik.appocoord.models.parts;

import org.springframework.lang.NonNull;

import java.io.Serializable;

public class PollInfo implements Serializable {
    @NonNull
    protected String title;
    protected String description;
    protected String location;

    public PollInfo(@NonNull String title, String description, String location) {
        this.title = title;
        this.description = description;
        this.location = location;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
