package com.frederik.appocoord.structures;

import com.frederik.appocoord.models.Poll;
import com.frederik.appocoord.models.User;
import com.frederik.appocoord.models.parts.TimeUserCollection;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

import java.util.ArrayList;

@Getter
@Setter
public class PollResponse extends Poll {
    @NonNull
    private String id;

    public PollResponse(String title, String description, String location, int end, ArrayList<TimeUserCollection> users, User creator, @NonNull String id) {
        super(title, description, location, end, users, creator);
        this.id = id;
    }
}
