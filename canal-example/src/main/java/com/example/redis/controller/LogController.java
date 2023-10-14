package com.example.redis.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author newRain
 * @description controller包下自动记录日志
 */
@RestController
public class LogController {

    @RequestMapping("/log/yes")
    @ResponseBody
    public String log(String name) {
        return "log";
    }
}