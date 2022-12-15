package com.example.delayqueue.delay;

import lombok.Data;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 订单对象
 *
 * @author newRain
 */
@Data
public class DelayOrderInfo implements Serializable, Delayed {

    private static final long serialVersionUID = 1L;
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 订单状态
     */
    private String status;
    /**
     * 订单过期时间
     */
    private String expTime;
    /**
     * 订单创建时间
     */
    private String createTime;

    /**
     * 用于延时队列内部比较排序：当前订单的过期时间 与 队列中对象的过期时间 比较
     */
    @Override
    public int compareTo(Delayed o) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long nowThreadTime = 0;
        long queueThreadTime = 0;
        try {
            nowThreadTime = formatter.parse(this.expTime).getTime();
            queueThreadTime = formatter.parse(((DelayOrderInfo) o).expTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Long.compare(nowThreadTime, queueThreadTime);
    }

    /**
     * 时间单位：秒
     * 延迟关闭时间 = 过期时间 - 当前时间
     */
    @Override
    public long getDelay(TimeUnit unit) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long time = 0;
        try {
            time = formatter.parse(this.expTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time - System.currentTimeMillis();
    }
}


