package com.example.algorithm;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * 精准分片算法
 * <p><em>Created on 2021/12/24 3:17 下午</em></p>
 *
 * @author fuzhongtang
 * @since 1.0.1
 */
public class MyPreciseShardingAlgorithm implements PreciseShardingAlgorithm<Long> {
    /**
     * tableNames 对应分片库中所有分片表的集合
     * shardingValue 为分片属性，其中 logicTableName 为逻辑表，columnName 分片健（字段），value 为从 SQL 中解析出的分片健的值
     */
    @Override
    public String doSharding(Collection<String> tableList, PreciseShardingValue<Long> shardingValue) {

        /*
          tang:分片指定的字段的值
          比如order表的order_id为分片字段
          这个值就是当前准备插入的记录的order_id的值
          我们根据order_id进行16的取余来决定插入哪个分表
          为何是16，因为这个表的就是做了16个分表
         */
        long id = shardingValue.getValue();
        long yu = id % 16;
        String targetTableName = shardingValue.getLogicTableName()+yu;

        for (String table: tableList) {
            if (table.equals(targetTableName)) {
                return table;
            }
        }
        throw new IllegalArgumentException("没有匹配到对应分表："+targetTableName);
    }
}

