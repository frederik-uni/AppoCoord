package com.frederik.appocoord;

import org.springframework.lang.NonNull;

import java.util.ArrayList;

public class PollResponse extends PollInfo {
    @NonNull
    private String id;
    @NonNull
    private ArrayList<TimeUserCollection> users;

    public PollResponse(@NonNull String title, String description, String location, @NonNull String id, @NonNull ArrayList<TimeUserCollection> users) {
        super(title, description, location);
        this.id = id;
        this.users = users;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @NonNull
    public ArrayList<TimeUserCollection> getUsers() {
        return users;
    }

    public void setUsers(@NonNull ArrayList<TimeUserCollection> users) {
        this.users = users;
    }
}
