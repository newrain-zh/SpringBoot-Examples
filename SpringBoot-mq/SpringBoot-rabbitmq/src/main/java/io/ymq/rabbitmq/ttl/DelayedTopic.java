package io.ymq.rabbitmq.ttl;

/**
 * 延时队列相关配置
 * @author new_rain
 */
public interface DelayedTopic {

    String DELAYED_EXCHANGE_NAME = "delay_exchange";
    String DELAYED_QUEUE_NAME = "delayed.queue";
    String DELAYED_ROUTING_KEY = "delayed.queue.routingkey";


}
