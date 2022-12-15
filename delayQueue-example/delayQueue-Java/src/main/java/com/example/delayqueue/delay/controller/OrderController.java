package com.example.delayqueue.delay.controller;


import com.example.delayqueue.delay.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/create/order")
    public String createOrder() {
        orderService.createOrder(0);
        return "success";
    }

}
