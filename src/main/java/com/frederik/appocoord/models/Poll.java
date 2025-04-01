package com.frederik.appocoord.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @NonNull
    public User getCreator() {
        return creator;
    }

    public Poll(String title, String description, String location, int end, @NonNull ArrayList<TimeUserCollection> users, @NonNull User creator) {
        super(title, description, location, end);
        this.users = users;
        this.creator = creator;
    }

    @JsonIgnore
    public void addTimeUserCollection(ReplyPollRequest data) {
        String id = data.getUser().getFingerprint();
        this.users = this.users.stream().filter(user -> !user.getUser().getFingerprint().equals(id)).collect(Collectors.toCollection(ArrayList::new));
        this.users.add(new TimeUserCollection(data.getUser(), data.getTimeInfo()));
    }

    public void setCreator(@NonNull User creator) {
        this.creator = creator;
    }

    @NonNull
    public ArrayList<TimeUserCollection> getUsers() {
        return users;
    }

    public void setUsers(@NonNull ArrayList<TimeUserCollection> users) {
        this.users = users;
    }

    public PollResponse getResponse(String id) {
        return new PollResponse(this.title, this.description, this.location, this.end, this.users, this.creator, id);
    }
}
