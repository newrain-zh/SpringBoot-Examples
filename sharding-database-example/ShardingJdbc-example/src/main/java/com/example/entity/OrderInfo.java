package com.example.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Description:
 *
 * @author: Administrator
 * Date: 2021-04-26
 */
@Entity
@Table(name = "t_order")
@Data
public class OrderInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private String orderName;

    private Integer orderStatus;

    private Long userId;
}
