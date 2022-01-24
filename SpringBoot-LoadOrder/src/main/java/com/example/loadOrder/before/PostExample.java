package com.example.loadOrder.before;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Order //没作用
public class PostExample {

    @PostConstruct
    public void post() {
        System.out.println("PostExample...");
    }
}
