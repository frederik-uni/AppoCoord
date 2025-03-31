package com.frederik.appocoord;


import com.frederik.appocoord.models.Poll;
import com.frederik.appocoord.structures.CreatePoll;
import com.frederik.appocoord.structures.PollResponse;
import com.frederik.appocoord.structures.TimeUserCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("")
public class Api {
    @Autowired
    private RedisService redisService;
    @PostMapping("/create")
    public PollResponse create(@RequestBody CreatePoll data) {
        String id = redisService.saveData(data.toPoll(this.redisService));
        Poll db_data = (Poll) redisService.getData(id);
        return db_data.getResponse(redisService, id);
    }

    @GetMapping("/info/{id}")
    public PollResponse info(@PathVariable String id) {
        Poll db_data = (Poll) redisService.getData(id);
        return db_data.getResponse(redisService, id);
    }

    @PostMapping("/reply/{id}")
    public PollResponse getUserById(@PathVariable String id, @RequestBody TimeUserCollection data) {
        return new PollResponse(null, "title", null, null, new ArrayList<>(), "", id);
    }
}
