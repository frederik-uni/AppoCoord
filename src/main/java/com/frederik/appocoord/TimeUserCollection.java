package com.frederik.appocoord;

import org.springframework.lang.NonNull;

public class TimeUserCollection {
    @NonNull
    private User user;
    @NonNull
    private TimeInfo timeInfo;

    public TimeUserCollection(@NonNull User user, @NonNull TimeInfo timeInfo) {
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
    public TimeInfo getTimeInfo() {
        return timeInfo;
    }

    public void setTimeInfo(@NonNull TimeInfo timeInfo) {
        this.timeInfo = timeInfo;
    }
}
