package com.frederik.appocoord.structures;

import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

public class TimeUserCollection implements Serializable {
    @NonNull
    private User user;
    @NonNull
    private ArrayList<TimeInfo> timeInfo;

    public TimeUserCollection(@NonNull User user, @NonNull  ArrayList<TimeInfo> timeInfo) {
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
    public  ArrayList<TimeInfo> getTimeInfo() {
        return timeInfo;
    }

    public void setTimeInfo(@NonNull  ArrayList<TimeInfo> timeInfo) {
        this.timeInfo = timeInfo;
    }
}
