package com.frederik.appocoord.structures;

import com.frederik.appocoord.models.User;
import com.frederik.appocoord.models.parts.TimeInfo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

import java.util.ArrayList;

@Getter
@Setter
public class ReplyPollRequest {
    @NonNull
    private User user;
    @NonNull
    private ArrayList<TimeInfo> timeInfo;

    public ReplyPollRequest(@NonNull User user, @NonNull ArrayList<TimeInfo> timeInfo) {
        this.user = user;
        this.timeInfo = timeInfo;
    }
}
