package com.example.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @project:
 * @author: hanning
 * @date: 2021/11/8 下午3:09
 */
@Mapper
public interface UserMapper {

    int count(@Param("appid") String appId);

}
