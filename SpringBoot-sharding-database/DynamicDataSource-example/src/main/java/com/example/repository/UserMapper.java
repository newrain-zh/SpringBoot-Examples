package com.example.repository;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @project:
 * @author: hanning
 * @date: 2021/11/8 下午3:09
 */
@Mapper
public interface UserMapper {

    @DS("#tenant")
    int count(@Param("appid") String appId, String tenant);

    @DS("#tenant")
    int count(@Param("tenant") String tenant);

}
