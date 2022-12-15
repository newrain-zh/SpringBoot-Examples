package com.example.delayqueue;


import com.example.delayqueue.delay.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrderTest {

    @Autowired
    private OrderService orderService;

    @Test
    public void test() {
        orderService.createOrder(0);
    }
}
