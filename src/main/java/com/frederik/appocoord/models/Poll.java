package com.frederik.appocoord.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.frederik.appocoord.RedisService;
import com.frederik.appocoord.structures.PollInfo;
import com.frederik.appocoord.structures.PollResponse;
import com.frederik.appocoord.structures.TimeUserCollection;
import com.frederik.appocoord.structures.User;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

public class Poll extends PollInfo implements Serializable {
    @NonNull
    private ArrayList<TimeUserCollection> users;
    @NonNull private String creator;
    public Poll(@NonNull String title, String description, String location, @NonNull ArrayList<TimeUserCollection> users, @NonNull String creator) {
        super(title, description, location);
        this.users = users;
        this.creator = creator;
    }

    @JsonIgnore
    public String getCreatorId() {
        return this.creator;
    }

    public void setCreator(@NonNull String creator) {
        this.creator = creator;
    }

    @NonNull
    public ArrayList<TimeUserCollection> getUsers() {
        return users;
    }

    public void setUsers(@NonNull ArrayList<TimeUserCollection> users) {
        this.users = users;
    }

    public PollResponse getResponse(RedisService redisService, String id) {
        return new PollResponse(redisService, this.title, this.description, this.location, this.users, this.creator, id);
    }
}
