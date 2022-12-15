package com.example.loadOrder.after;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author newRain
 */
@Component
public class ApplicationRunnerExample implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("ApplicationRunnerExample...");
        System.out.println("ApplicationRunnerExample args=" + args);
    }
}
