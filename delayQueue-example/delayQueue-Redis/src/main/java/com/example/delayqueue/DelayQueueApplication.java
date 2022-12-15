package com.example.delayqueue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author newRain
 */
@SpringBootApplication
@EnableScheduling
public class DelayQueueApplication {

    public static void main(String[] args) {
        SpringApplication.run(DelayQueueApplication.class, args);
    }


}
