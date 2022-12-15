package com.example.delayqueue.delay.service;


import com.example.delayqueue.delay.DelayOrderInfo;
import com.example.delayqueue.delay.OrderOverTimeClose;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class OrderService {

    static String[] str = new String[]{"成功", "支付中", "订单初始化"};

    @Autowired
    private OrderOverTimeClose orderOverTimeClose;

    static ExecutorService service = Executors.newFixedThreadPool(100);


    public static String getTime(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(time);
        return formatter.format(date);
    }

    /**
     * 模拟订单创建
     *
     * @param userId
     */
    public void createOrder(int userId) {
        // 创建初始订单
        long createTime = System.currentTimeMillis();
        String currentTime = getTime(createTime);
        // 十秒后超时
        String overTime = getTime(createTime + 10000);
        String orderNo = String.valueOf(new Random().nextLong());
        DelayOrderInfo order = new DelayOrderInfo();
        order.setOrderNo(orderNo);
        order.setExpTime(overTime);
        int randomIndex = (int) (Math.random() * str.length);
        // 随机分配
        order.setStatus(str[randomIndex]);
        order.setCreateTime(currentTime);
        service.execute(() -> orderOverTimeClose.orderPutQueue(order, currentTime, overTime));
    }
}
