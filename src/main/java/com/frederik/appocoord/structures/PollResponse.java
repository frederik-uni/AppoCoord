package com.frederik.appocoord.structures;

import com.frederik.appocoord.models.Poll;
import org.springframework.lang.NonNull;

import java.util.ArrayList;

public class PollResponse extends Poll {
    @NonNull
    private String id;

    public PollResponse(@NonNull String title, String description, String location, @NonNull ArrayList<TimeUserCollection> users, @NonNull String creator, @NonNull String id) {
        super(title, description, location, users, creator);
        this.id = id;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }
}
