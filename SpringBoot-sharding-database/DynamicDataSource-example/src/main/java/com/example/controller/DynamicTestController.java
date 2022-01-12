package com.example.controller;

import com.example.entity.FindUserParams;
import com.example.entity.Result;
import com.example.service.MasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author newRain
 * @description 动态路由测试
 */
@RestController
@RequestMapping("/dynamic")
public class DynamicTestController {
    @Autowired
    private MasterService masterService;

    @RequestMapping("/header")
    public Result<Integer> findByHeader() {
        return Result.ok(masterService.findByHeader());
    }

    @RequestMapping("/entity")
    public Result<Integer> findByEntity(@RequestBody FindUserParams findUserParams) {
        return Result.ok(masterService.findByEntity(findUserParams));
    }

    @RequestMapping("/params")
    public Result<Integer> findByParams(String tenant) {
        return Result.ok(masterService.findByParams(tenant));
    }


    @RequestMapping("/master")
    public Result<Integer> find() {
        return Result.ok(masterService.find());
    }

    @RequestMapping("/ds")
    public Result<Integer> findByDs() {
        return Result.ok(masterService.findByDS());
    }
}
