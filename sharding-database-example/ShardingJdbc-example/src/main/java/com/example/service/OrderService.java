package com.example.service;

import com.example.entity.OrderInfo;
import com.example.repository.OrderInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {


    @Autowired
    private OrderInfoRepository orderInfoRepository;

    public void save(Integer count) {
        for (int j = 0; j < count; j++) {
            for (int i = 0; i < count; i++) {
                OrderInfo orderInfo = new OrderInfo();
                orderInfo.setOrderName("霹雳防寒金缕衣_" + j + "_" + i);
                orderInfo.setOrderStatus(1);
                orderInfo.setUserId((long) j);
                orderInfoRepository.save(orderInfo);
            }
        }

    }

    /**
     * 查询全部订单信息
     *
     * @return 全部订单信息
     */
    public List<OrderInfo> getAll() {
        List<OrderInfo> all = orderInfoRepository.findAll();
        return all;
    }

    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    public Optional findById(Long id) {
        return orderInfoRepository.findById(id);
    }
}
