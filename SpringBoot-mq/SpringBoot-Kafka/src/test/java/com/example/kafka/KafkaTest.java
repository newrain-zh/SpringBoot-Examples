package com.example.kafka;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootTest
class KafkaTest {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Test
    void send() {
        for (int i = 0; i < 999; i++) {
            kafkaTemplate.send("kafka-test", "this test msg! =" + i);
        }
    }

}
