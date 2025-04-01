package com.frederik.appocoord.structures;


import com.frederik.appocoord.RedisService;
import com.frederik.appocoord.models.Poll;
import com.frederik.appocoord.models.User;
import com.frederik.appocoord.models.parts.PollInfo;
import com.frederik.appocoord.models.parts.TimeInfo;
import com.frederik.appocoord.models.parts.TimeUserCollection;
import org.springframework.lang.NonNull;

import java.util.ArrayList;

public class CreatePollRequest extends PollInfo {
    @NonNull
    private User uploader;
    @NonNull
    private ArrayList<TimeInfo> available_times;

    public CreatePollRequest(String title, String description, String location, int end, @NonNull User uploader, @NonNull ArrayList<TimeInfo> available_times) {
        super(title, description, location, end);
        this.uploader = uploader;
        this.available_times = available_times;
    }

    @NonNull
    public User getUploader() {
        return uploader;
    }

    public void setUploader(@NonNull User uploader) {
        this.uploader = uploader;
    }

    @NonNull
    public ArrayList<TimeInfo> getAvailable_times() {
        return available_times;
    }

    public void setAvailable_times(@NonNull ArrayList<TimeInfo> available_times) {
        this.available_times = available_times;
    }

    public Poll toPoll(RedisService redisService) {
        var timeInfo = new ArrayList<TimeUserCollection>();
        String id = redisService.createIf(this.uploader.getFingerprintInternal(), this.uploader);
        if (!this.available_times.isEmpty()) {
            timeInfo.add(new TimeUserCollection(redisService, id, this.available_times));
        }

        return new Poll(this.title, this.description, this.location, this.end, timeInfo, id);
    }
}
