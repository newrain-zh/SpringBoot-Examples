package io.ymq.rabbitmq.ttl;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

/**
 * 消息生产者
 *
 * @author new_rain
 */
@Component
public class DelayedSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    /**
     * 发送延时消息
     */
    public void delayedMessage() {
        String context = "test delayed message";
        System.out.println("Send time:" + LocalTime.now() + "send:" + context);
        rabbitTemplate.convertAndSend(DelayedTopic.DELAYED_EXCHANGE_NAME, DelayedTopic.DELAYED_ROUTING_KEY, context, a -> {
            a.getMessageProperties().setDelay(3000);
            return a;
        });
    }

}
