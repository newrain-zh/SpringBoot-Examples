package com.example.redis.controller;

import com.example.redis.dto.simple.UserParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author pdai
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * http://localhost:8080/user/add .
     *
     * @param userParam user param
     * @return user
     */
    @PostMapping("add")
    public ResponseEntity<UserParam> add(@Valid @RequestBody UserParam userParam) {
        return ResponseEntity.ok(userParam);
    }
}