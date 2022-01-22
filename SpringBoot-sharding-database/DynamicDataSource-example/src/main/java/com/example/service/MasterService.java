package com.example.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.example.entity.FindUserParams;
import com.example.repository.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
        int count = wechatUserMapper.count("wxbfd8f632ef3eba3b");
        log.info("findByHeader:{}", count);
        return count();
    }

    public int count() {
        String tenant = DynamicDataSourceContextHolder.peek();
        log.info("count() start dataSource:{}", DynamicDataSourceContextHolder.peek());
//        DynamicDataSourceContextHolder.push("ds0");
//        log.info("count() dataSource:{}", DynamicDataSourceContextHolder.peek());
        Stream<Integer> integerStream = Stream.of(1, 2, 3, 4, 5, 6);
        integerStream.parallel().forEach(integer -> {
            findByDS(tenant);
        });
      /*  new Thread(() -> {
            findByDS(tenant);
        }).start();*/
        log.info("count() end dataSource:{}", DynamicDataSourceContextHolder.peek());
        int count = wechatUserMapper.count("wxbfd8f632ef3eba3b");
        log.info("count() end dataSource:{}", DynamicDataSourceContextHolder.peek());
        return wechatUserMapper.count("wxbfd8f632ef3eba3b");
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
//    @DS("#tenant")
    public int findByParams(String tenant) {
      /*  Stream<Integer> integerStream = Stream.of(1, 2, 3, 4, 5, 6);
        integerStream.parallel().forEach(integer -> {
            int count = wechatUserMapper.count("wxbfd8f632ef3eba3b", tenant);
            log.info("count:{}", count);
        });*/
        return wechatUserMapper.count(tenant);
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
    public int findByDS(String tenant) {
//        DynamicDataSourceContextHolder.push("master");
        log.info("findByDS() params:{},dataSource:{}", tenant, DynamicDataSourceContextHolder.peek());
//        int count = wechatUserMapper.count("wxbfd8f632ef3eba3b");
        int count = wechatUserMapper.count("wxbfd8f632ef3eba3b", tenant);
//        DynamicDataSourceContextHolder.poll();
        log.info("findByDS() result:{},dataSource:{}", count, DynamicDataSourceContextHolder.peek());
        return count;
    }
}
