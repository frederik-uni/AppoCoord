package com.frederik.appocoord.structures;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.frederik.appocoord.models.Poll;
import com.frederik.appocoord.models.User;
import com.frederik.appocoord.models.parts.PollInfo;
import com.frederik.appocoord.models.parts.TimeInfo;
import com.frederik.appocoord.models.parts.TimeUserCollection;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

import java.util.ArrayList;

@Getter
@Setter
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

    @JsonIgnore
    public Poll toPoll() {
        var timeInfo = new ArrayList<TimeUserCollection>();
        if (!this.available_times.isEmpty()) {
            timeInfo.add(new TimeUserCollection(this.uploader, this.available_times));
        }

        return new Poll(this.title, this.description, this.location, this.end, timeInfo, this.uploader);
    }
}
