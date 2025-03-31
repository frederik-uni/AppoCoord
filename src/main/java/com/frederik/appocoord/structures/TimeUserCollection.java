package com.frederik.appocoord.structures;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.frederik.appocoord.RedisService;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

public class TimeUserCollection implements Serializable {
    @NonNull
    private String user_id;
    @NonNull
    private ArrayList<TimeInfo> timeInfo;
    @JsonIgnore
    private transient RedisService redisService;

    public TimeUserCollection(RedisService redisService, @NonNull String user_id, @NonNull ArrayList<TimeInfo> timeInfo) {
        this.user_id = user_id;
        this.timeInfo = timeInfo;
        this.redisService = redisService;
    }

    @NonNull
    public String getUserId() {
        return user_id;
    }

    public User getUser() {
        System.out.println(redisService);
        return (User) redisService.getData(this.user_id);
    }

    public void setRedis(RedisService redisService) {
        this.redisService = redisService;
    }

    @JsonIgnore
    public void setUserId(@NonNull String user_id) {
        this.user_id = user_id;
    }

    @NonNull
    public  ArrayList<TimeInfo> getTimeInfo() {
        return timeInfo;
    }

    public void setTimeInfo(@NonNull  ArrayList<TimeInfo> timeInfo) {
        this.timeInfo = timeInfo;
    }
}
