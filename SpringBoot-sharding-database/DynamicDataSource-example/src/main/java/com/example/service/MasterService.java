package com.example.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.example.entity.FindUserParams;
import com.example.repository.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
@Slf4j
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
        int count = wechatUserMapper.count();
        log.info("findByHeader:{}", count);
        return count;
    }


    /**
     * 从实体类获取数据源
     *
     * @param findUserParams
     * @return
     */
    @DS("#findUserParams.tenant")
    public int findByEntity(FindUserParams findUserParams) {
        return wechatUserMapper.count();
    }

    /**
     * 从方法入参获取
     *
     * @param tenant
     * @return
     */
    @DS("#tenant")
    public int findByParams(String tenant) {
        return wechatUserMapper.count();
    }

    /**
     * 手动指定主库
     *
     * @return
     */
    @DS(value = "ds1")
    public int find() {
        return wechatUserMapper.count();
    }

    /**
     * 手动指定从库
     *
     * @return
     */
    @DS(value = "ds0")
    public int find(String tenant) {
        return wechatUserMapper.count();
    }

    /**
     * 异步操作+异步操作 无法切库
     *
     * @param tenant
     */
    @Async
    @DS("#tenant")
    public void asyncByAsync(String tenant) {
        async();
    }

    public void async() {
        String currentDataSource = DynamicDataSourceContextHolder.peek();
        int outCount = wechatUserMapper.count();
        //currentDataSourceOut=ds1 count:2
        log.info("currentDataSourceOut={} count:{}", currentDataSource, outCount);
        Stream<Integer> integerStream = Stream.of(1, 2, 3, 4, 5, 6);
        new Thread(() -> {
            String threadDatasource = DynamicDataSourceContextHolder.peek();
            int count = wechatUserMapper.count();
            //currentDataSourceOut=null count:2
            log.info("currentDataSourceThread={} count={}", threadDatasource, count);
        }).start();
        integerStream.parallel().forEach(integer -> {
            String parallelDataSource = DynamicDataSourceContextHolder.peek();
            new Thread(() -> {
                //currentDataSourceIn=null count=1
                int count = wechatUserMapper.count();
                log.info("currentDataSourceIn={} count={}", parallelDataSource, count);
            }).start();
        });
    }

    /**
     * 异步操作 切库没有问题
     *
     * @param tenant
     */
    @Async
    @DS("#tenant")
    public void async(String tenant) {
        String threadDatasource = DynamicDataSourceContextHolder.peek();
        int count = wechatUserMapper.count();
        //currentDataSourceOut=null count:2
        log.info("currentDataSourceThread={} count={}", threadDatasource, count);
    }
}
