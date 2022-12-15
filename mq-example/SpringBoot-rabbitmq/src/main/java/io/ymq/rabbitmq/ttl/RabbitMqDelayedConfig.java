package io.ymq.rabbitmq.ttl;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 延时队列示例
 *
 * @author new_rain
 */
@Configuration
public class RabbitMqDelayedConfig {


    /**
     * 声明队列
     * @return
     */
    @Bean
    public Queue delayedQueue() {
        return new Queue(DelayedTopic.DELAYED_QUEUE_NAME);
    }

    /**
     * 自定义Exchange
     * @return
     */
    @Bean
    public CustomExchange delayedExchange() {
        Map<String, Object> args = new HashMap<>(2);
        args.put("x-delayed-type", "direct");
        return new CustomExchange(DelayedTopic.DELAYED_EXCHANGE_NAME, "x-delayed-message", true, false, args);
    }

    /**
     * 将队列和Exchange绑定
     * @param queue
     * @param customExchange
     * @return
     */
    @Bean
    public Binding bindingNotify(@Qualifier("delayedQueue") Queue queue,
                                 @Qualifier("delayedExchange") CustomExchange customExchange) {
        return BindingBuilder.bind(queue).to(customExchange).with(DelayedTopic.DELAYED_ROUTING_KEY).noargs();
    }
}
