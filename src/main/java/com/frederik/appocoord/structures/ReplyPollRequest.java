package com.frederik.appocoord.structures;

import com.frederik.appocoord.models.User;
import com.frederik.appocoord.models.parts.TimeInfo;
import org.springframework.lang.NonNull;

import java.util.ArrayList;

public class ReplyPollRequest {
    @NonNull
    private User user;
    @NonNull
    private ArrayList<TimeInfo> timeInfo;

    public ReplyPollRequest(@NonNull User user, @NonNull ArrayList<TimeInfo> timeInfo) {
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
