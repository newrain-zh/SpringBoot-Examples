package com.example.rocketmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = "tx-group", topic = "tx-rocketmq", messageModel = MessageModel.BROADCASTING)
public class TransactionConsumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String s) {
        log.info("send transaction mssage parma is:{}", s);
    }
}
