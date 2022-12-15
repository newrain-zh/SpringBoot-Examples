package com.example.loadOrder.after;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author newRain
 */
@Component
public class CommandLineRunnerExample implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("CommandLineRunnerExample ...");
    }
}
