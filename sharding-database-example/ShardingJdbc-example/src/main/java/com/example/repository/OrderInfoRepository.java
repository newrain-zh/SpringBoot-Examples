package com.example.repository;

import com.example.entity.OrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Description:
 *
 * @author: Administrator
 * Date: 2021-04-26
 */
@Repository
public interface OrderInfoRepository extends JpaRepository<OrderInfo, Long> {

}
