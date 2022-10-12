package io.ymq.rabbitmq.ttl;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalTime;

/**
 * 延时消息消费者
 *
 * @author new_rain
 */
@Component
public class DelayedReceiver {

    @RabbitListener(queues = DelayedTopic.DELAYED_QUEUE_NAME)
    public void receive(Message message, Channel channel) throws IOException {
        String s = new String(message.getBody());
        System.out.println("Received time:" + LocalTime.now() + " Received:" + s);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
    }
}
