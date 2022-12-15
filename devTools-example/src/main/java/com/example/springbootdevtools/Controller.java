package com.example.springbootdevtools;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/test/dev")
    public String test() {
        return "prod";
    }
}
