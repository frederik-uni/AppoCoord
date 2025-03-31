package com.frederik.appocoord.structures;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.frederik.appocoord.RedisService;
import com.frederik.appocoord.models.Poll;
import com.frederik.appocoord.models.User;
import com.frederik.appocoord.models.parts.TimeUserCollection;
import org.springframework.lang.NonNull;

import java.util.ArrayList;

public class PollResponse extends Poll {
    @NonNull
    private String id;
    @JsonIgnore
    private RedisService redisService;

    public PollResponse(RedisService redisService, @NonNull String title, String description, String location, @NonNull ArrayList<TimeUserCollection> users, @NonNull String creator, @NonNull String id) {
        super(title, description, location, users, creator);
        this.id = id;
        this.redisService = redisService;
    }

    @NonNull
    public User getCreator() {
        return (User) redisService.getData(getCreatorId());
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }
}
