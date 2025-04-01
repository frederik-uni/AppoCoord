package com.frederik.appocoord;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HtmlForwardingController {

    @GetMapping("/{path:[^.]+}")
    public String forwardHtml(@PathVariable("path") String path) {
        return "forward:/index.html";
    }
}
