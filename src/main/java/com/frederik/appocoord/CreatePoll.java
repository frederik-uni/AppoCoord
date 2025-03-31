package com.frederik.appocoord;


import org.springframework.lang.NonNull;

import java.util.ArrayList;

public class CreatePoll extends PollInfo {
    @NonNull
    private User uploader;
    @NonNull
    private ArrayList<TimeInfo> available_times;

    public CreatePoll(@NonNull String title, String description, String location, @NonNull User uploader, @NonNull ArrayList<TimeInfo> available_times) {
        super(title, description, location);
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
}
