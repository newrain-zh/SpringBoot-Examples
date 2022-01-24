package com.example;

import com.example.repository.UserMapper;
import com.example.service.MasterService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class DynamicDataSourceTest {

    @Autowired
    private UserMapper wechatUserMapper;

    @Autowired
    private MasterService masterService;

    public void selectionDataSource() {
        //        DynamicDataSourceContextHolder.push("master");

    }

    /**
     * 异步+异步操作
     */
    @Test
    public void asyncByAsyncTest() {
        masterService.asyncByAsync("ds1");
    }

    @Test
    public void asyncTest() {
        masterService.async("ds1");
    }
}
