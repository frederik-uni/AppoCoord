package com.frederik.appocoord.models.parts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.frederik.appocoord.models.User;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

public class TimeUserCollection implements Serializable {
    @NonNull
    private User user;
    @NonNull
    private ArrayList<TimeInfo> timeInfo;

    @JsonCreator
    public TimeUserCollection(@JsonProperty("user") @NonNull User user, @JsonProperty("timeInfo") @NonNull ArrayList<TimeInfo> timeInfo) {
        this.user = user;
        this.timeInfo = timeInfo;
    }

    @NonNull
    public User getUser() {
        return user;
    }

    public void setUser(@NonNull User user) {
        this.user = user;
    }

    @NonNull
    public ArrayList<TimeInfo> getTimeInfo() {
        return timeInfo;
    }

    public void setTimeInfo(@NonNull ArrayList<TimeInfo> timeInfo) {
        this.timeInfo = timeInfo;
    }
}
