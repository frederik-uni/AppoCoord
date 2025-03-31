package com.frederik.appocoord;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class Api {
    @PostMapping("/greet")
    public String greetUser(@RequestBody String name) {
        return "Hello, " + name + "!";
    }

    @GetMapping("/user/{id}")
    public String getUserById(@PathVariable int id) {
        return "User ID: " + id;
    }
}
