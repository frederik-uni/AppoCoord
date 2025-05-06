package com.frederik.appocoord;


import com.frederik.appocoord.models.Poll;
import com.frederik.appocoord.models.parts.TimeUserCollection;
import com.frederik.appocoord.structures.CreatePollRequest;
import com.frederik.appocoord.structures.PollResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class Api {
    @Autowired
    private RedisService redisService;

    @PostMapping("/create")
    public PollResponse create(@RequestBody CreatePollRequest data) {
        String id = redisService.saveData(data.toPoll());
        Poll db_data = redisService.getData(id, Poll.class);
        return db_data.getResponse(id, db_data.getCreator().getFingerprint());
    }

    @GetMapping("/info/{id}")
    public PollResponse info(@PathVariable String id, @RequestHeader(value = "fingerprint", required = true) String fingerprint) {
        Poll db_data = redisService.getData(id, Poll.class);
        return db_data.getResponse(id, fingerprint);
    }

    @PostMapping("/reply/{id}")
    public PollResponse getUserById(@PathVariable String id, @RequestBody TimeUserCollection data) {
        Poll db_data = redisService.getData(id, Poll.class);
        db_data.addTimeUserCollection(data);
        redisService.createOrUpdate(id, db_data);

        return db_data.getResponse(id, data.getUser().getFingerprint());
    }
}
