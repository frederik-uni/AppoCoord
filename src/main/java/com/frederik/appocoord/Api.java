package com.frederik.appocoord;


import com.frederik.appocoord.models.Poll;
import com.frederik.appocoord.structures.CreatePollRequest;
import com.frederik.appocoord.structures.PollResponse;
import com.frederik.appocoord.structures.ReplyPollRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class Api {
    @Autowired
    private RedisService redisService;

    @PostMapping("/create")
    @CrossOrigin(origins = "*")
    public PollResponse create(@RequestBody CreatePollRequest data) {
        String id = redisService.saveData(data.toPoll());
        Poll db_data = (Poll) redisService.getData(id);
        return db_data.getResponse(id, db_data.getCreator().getFingerprint().substring(0));
    }

    @GetMapping("/info/{id}")
    @CrossOrigin(origins = "*")
    public PollResponse info(@PathVariable String id, @RequestHeader(value = "fingerprint", required = true) String fingerprint) {
        Poll db_data = (Poll) redisService.getData(id);
        return db_data.getResponse(id, fingerprint);
    }

    @PostMapping("/reply/{id}")
    @CrossOrigin(origins = "*")
    public PollResponse getUserById(@PathVariable String id, @RequestBody ReplyPollRequest data) {
        Poll db_data = (Poll) redisService.getData(id);
        db_data.addTimeUserCollection(data);
        redisService.createOrUpdate(id, db_data);

        return db_data.getResponse(id, data.getUser().getFingerprint().substring(0));
    }
}
