package com.example.delayqueue.zset;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Set;

/**
 * 延迟队列
 *
 * @author newRain
 * @description Redis zSet 延迟队列实现
 */
@Component
@Slf4j
public class DelayQueueJob {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public static final String DELAY_KEY = "DELAY_QUEUE_TEST";

    @Scheduled(fixedRate = 1000)
    public void scheduledTask() {
        //当前时间
        long now = System.currentTimeMillis();
        long before = now - 10000;
        log.info("before end:{} {}", longToString(before), longToString(now));
        Set<String> strings = stringRedisTemplate.opsForZSet().rangeByScore(DELAY_KEY, before, now);
        if (CollectionUtils.isEmpty(strings)) {
            return;
        }
        //单个服务的情况可以如下实现
        strings.parallelStream().forEach(member -> {
            log.info("process member:{} time:{}", member, longToString(System.currentTimeMillis()));
            //在分布式环境下 例如本服务有多副本机制则要考虑多线程情况下幂等情况
            //最简单的如下进行删除操作 失败进行记录和重试、补偿手段
            //业务处理中若有数据库也可以用数据库的记录来保障幂等处理
            Long count = stringRedisTemplate.opsForZSet().remove(DELAY_KEY, member);
            if (count != null && count > 0) {
                try {
                    //do something
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    log.error("save fail record");
                }
            }
        });
        //单台服务的情况下，可以使用批量删除提高性能
//      stringRedisTemplate.opsForZSet().remove(DELAY_KEY, strings.toArray());
    }

    /**
     * 入列
     */
    @Scheduled(fixedRate = 500)
    public void offerDelayQueue() {
        long currentTimeMillis = System.currentTimeMillis();
        String dateTime = longToString(currentTimeMillis);
        //score为当前时间+延时时间
        //在使用zSet要注意member内容是不重复的，否则会更新score，这样会使得任务丢失。
        stringRedisTemplate.opsForZSet().add(DELAY_KEY, dateTime, currentTimeMillis + 10000);
    }

    /**
     * 时间格式化 便于日志查看
     *
     * @param timestamp
     * @return
     */
    private static String longToString(long timestamp) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
    }

    public static void main(String[] args) {
        System.out.println(longToString(System.currentTimeMillis()));
    }
}
