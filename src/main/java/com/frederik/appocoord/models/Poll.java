package com.frederik.appocoord.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.frederik.appocoord.RedisService;
import com.frederik.appocoord.models.parts.PollInfo;
import com.frederik.appocoord.models.parts.TimeUserCollection;
import com.frederik.appocoord.structures.PollResponse;
import com.frederik.appocoord.structures.ReplyPollRequest;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Poll extends PollInfo implements Serializable {
    @NonNull
    private ArrayList<TimeUserCollection> users;
    @NonNull
    private String creator;

    public Poll(String title, String description, String location, int end, @NonNull ArrayList<TimeUserCollection> users, @NonNull String creator) {
        super(title, description, location, end);
        this.users = users;
        this.creator = creator;
    }

    @JsonIgnore
    public String getCreatorId() {
        return this.creator;
    }

    @JsonIgnore
    public void addTimeUserCollection(RedisService redisService, ReplyPollRequest data) {
        String id = redisService.createIf(data.getUser().getFingerprintInternal(), data.getUser());
        this.users = this.users.stream().filter(user -> !user.getUserId().equals(id)).collect(Collectors.toCollection(ArrayList::new));
        this.users.add(new TimeUserCollection(id, data.getTimeInfo()));
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
        return new PollResponse(this.title, this.description, this.location, this.end, this.users, this.creator, id, redisService);
    }
}
