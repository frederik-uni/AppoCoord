package com.frederik.appocoord.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    private User creator;

    @JsonCreator
    public Poll(@JsonProperty("title") String title, @JsonProperty("description") String description, @JsonProperty("location") String location, @JsonProperty("end") int end, @JsonProperty("users") @NonNull ArrayList<TimeUserCollection> users, @JsonProperty("creator") @NonNull User creator) {
        super(title, description, location, end);
        this.users = users;
        this.creator = creator;
    }

    @NonNull
    public User getCreator() {
        return creator;
    }

    public void setCreator(@NonNull User creator) {
        this.creator = creator;
    }

    @JsonIgnore
    public void addTimeUserCollection(ReplyPollRequest data) {
        String id = data.getUser().getFingerprint();
        this.users = this.users.stream().filter(user -> !user.getUser().getFingerprint().equals(id)).collect(Collectors.toCollection(ArrayList::new));
        this.users.add(new TimeUserCollection(data.getUser(), data.getTimeInfo()));
    }

    @NonNull
    public ArrayList<TimeUserCollection> getUsers() {
        return users;
    }

    public void setUsers(@NonNull ArrayList<TimeUserCollection> users) {
        this.users = users;
    }


    @JsonIgnore
    public PollResponse getResponse(String id, String userId) {
        this.creator.censor(userId);
        this.users.forEach(v -> v.getUser().censor(userId));
        return new PollResponse(this.title, this.description, this.location, this.end, this.users, this.creator, id);
    }
}
