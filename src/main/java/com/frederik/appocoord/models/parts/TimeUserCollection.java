package com.frederik.appocoord.models.parts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.frederik.appocoord.RedisService;
import com.frederik.appocoord.SpringContext;
import com.frederik.appocoord.models.User;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

public class TimeUserCollection implements Serializable {
    @NonNull
    private String user_id;
    @NonNull
    private ArrayList<TimeInfo> timeInfo;

    public TimeUserCollection(@NonNull String user_id, @NonNull ArrayList<TimeInfo> timeInfo) {
        this.user_id = user_id;
        this.timeInfo = timeInfo;
    }

    @NonNull
    public String getUserId() {
        return user_id;
    }

    @JsonIgnore
    public void setUserId(@NonNull String user_id) {
        this.user_id = user_id;
    }

    @NonNull
    public User getUser() {
        return (User) SpringContext.getBean(RedisService.class).getData(this.user_id);
    }


    @NonNull
    public ArrayList<TimeInfo> getTimeInfo() {
        return timeInfo;
    }

    public void setTimeInfo(@NonNull ArrayList<TimeInfo> timeInfo) {
        this.timeInfo = timeInfo;
    }
}
