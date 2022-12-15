package com.example;

import com.example.entity.OrderInfo;
import com.example.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class StartApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private OrderService orderService;

    @Test
    void testSave() {
        orderService.save(10);
    }

    @Test
    void findById() {
        Optional byId = orderService.findById(681871235621060608L);
        System.out.println(byId.orElse(null));
    }

    @Test
    void findAll() {
        List<OrderInfo> all = orderService.getAll();
        System.out.println(all.size());
    }
}
