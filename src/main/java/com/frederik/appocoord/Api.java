package com.frederik.appocoord;


import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("")
public class Api {
    @PostMapping("/create")
    public PollResponse create(@RequestBody CreatePoll data) {
        return new PollResponse("title", null, null, "id", new ArrayList<>());
    }

    @GetMapping("/info/{id}")
    public PollResponse info(@PathVariable String id) {
        return new PollResponse("title", null, null, "id", new ArrayList<>());
    }

    @GetMapping("/user/{id}")
    public String getUserById(@PathVariable int id) {
        return "User ID: " + id;
    }
}
