package com.example.rocketmq.test;

import com.example.rocketmq.consumer.MQConsumerConfigure;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Test {

    @Autowired
    MQConsumerConfigure mqConsumerConfigure;

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @org.junit.Test
    public void test() {
        try {
            Message message = new Message();
            message.setBody("test message ".getBytes());
            message.setTopic("tx-rocketmq");
            System.out.println("========sending message=========");
            TransactionSendResult transactionSendResult = rocketMQTemplate.sendMessageInTransaction("rocketmq-example", MessageBuilder.withPayload(message).build(), null);
            System.out.println("========finish send =========");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
}
