package com.example.entity;


import lombok.Data;

@Data
public class FindUserParams {
    private String openId;
    private String appid;
    private String tenant;

    public FindUserParams() {
    }
}
