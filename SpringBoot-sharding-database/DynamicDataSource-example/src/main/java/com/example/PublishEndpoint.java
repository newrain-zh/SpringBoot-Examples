package com.example;

import com.example.service.MasterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order
@Slf4j
public class PublishEndpoint implements ApplicationRunner {

    @Autowired
    private MasterService masterService;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        //do something
        System.out.println("加载完成。。。。");
        int ds0 = masterService.findByParams("ds2");
        int ds1 = masterService.findByParams("ds1");
        log.info("ds0={},d1={}", ds0, ds1);
    }
}
