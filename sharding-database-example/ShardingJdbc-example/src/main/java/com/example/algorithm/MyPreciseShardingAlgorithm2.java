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
public class MyPreciseShardingAlgorithm2 implements PreciseShardingAlgorithm<Long> {

    private static int index = 0;

    /**
     * tableNames 对应分片库中所有分片表的集合
     * shardingValue 为分片属性，其中 logicTableName 为逻辑表，columnName 分片健（字段），value 为从 SQL 中解析出的分片健的值
     */
    @Override
    public String doSharding(Collection<String> tableList, PreciseShardingValue<Long> shardingValue) {

        /*
          tang:自定义分片，采用最简单的自然轮流
         */
        index++ ;
        if (index >= tableList.size())
        {
            index = 0;
        }

        String targetTableName = shardingValue.getLogicTableName()+index;
        if (tableList.contains(targetTableName))
        {
            return targetTableName;
        }

        throw new IllegalArgumentException("没有匹配到对应分表："+targetTableName);
    }
}

