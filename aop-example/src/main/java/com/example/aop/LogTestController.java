package com.example.aop;

import com.example.aop.anotation.Log;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author newRain
 * @description 使用注解来记
 */
@RestController
public class LogTestController {

    @RequestMapping("/log/no")
    @ResponseBody
    @Log
    public String log(String name) {
        return "/log/no";
    }
}
