package com.example.kafka.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 * @author newRain
 */
@Component
@Slf4j
public class KafkaConsumer {

    @KafkaListener(topics = {"kafka-test"}, groupId = "zhTestGroup")
    public void onMessage(ConsumerRecord<?, ?> record, Acknowledgment ack) {
        // 消费的哪个topic、partition的消息,打印出消息内容
        log.info("简单消费 topic={} partition={} content={}", record.topic(), record.partition(), record.value());
        ack.acknowledge();
    }

}
