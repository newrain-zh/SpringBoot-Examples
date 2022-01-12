package com.example.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.example.entity.FindUserParams;
import com.example.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MasterService {

    @Autowired
    private UserMapper wechatUserMapper;

    /**
     * 从头部获取数据源
     * httpRequest 请求头需要添加tenant=xxx
     *
     * @return
     */
    @DS("#header.tenant")
    public int findByHeader() {
        return wechatUserMapper.count("wx######");
    }

    /**
     * 从实体类获取数据源
     *
     * @param findUserParams
     * @return
     */
    @DS("#findUserParams.tenant")
    public int findByEntity(FindUserParams findUserParams) {
        return wechatUserMapper.count("wx######");
    }

    /**
     * 从方法入参获取
     *
     * @param tenant
     * @return
     */
    @DS("#tenant")
    public int findByParams(String tenant) {
        return wechatUserMapper.count("wx######");
    }

    /**
     * 手动指定主库
     *
     * @return
     */
    @DS(value = "master")
    public int find() {
        return wechatUserMapper.count("wx######");
    }

    /**
     * 手动指定从库
     *
     * @return
     */
    @DS("ds0")
    public int findByDS() {
        return wechatUserMapper.count("wx######");
    }
}
