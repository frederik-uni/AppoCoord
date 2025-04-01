package com.frederik.appocoord.structures;

import com.frederik.appocoord.models.Poll;
import com.frederik.appocoord.models.User;
import com.frederik.appocoord.models.parts.TimeUserCollection;
import org.springframework.lang.NonNull;

import java.util.ArrayList;

public class PollResponse extends Poll {
    @NonNull
    private String id;

    public PollResponse(String title, String description, String location, int end, ArrayList<TimeUserCollection> users, User creator, @NonNull String id) {
        super(title, description, location, end, users, creator);
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
