package io.ymq.rabbitmq.run;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * 描述: 启动服务
 *
 * @author: yanpenglei
 * @create: 2017/10/23 14:14
 */
@SpringBootApplication
@ComponentScan(value = {"io.ymq.rabbitmq"})
public class Startup {

    public static void main(String[] args) {
        SpringApplication.run(Startup.class, args);
    }


    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory defaultConnectionFactory){
        return new RabbitAdmin(defaultConnectionFactory);
    }
    /*@Bean
    public Queue ttlQueue() {
        Map<String, Object> arguments = new HashMap<>();
        // 设置3s过期
        arguments.put("x-message-ttl", 3000);
        return new Queue("topicQueue1", false, false, false, arguments);
    }

    // DLX队列示例
    @Bean
    public Queue dlxQueue() {
        Map<String, Object> arguments = new HashMap<>();
        // 指定消息死亡后发送到ExchangeName="dlx.exchange"的交换机去
        arguments.put("x-dead-letter-exchange","dlx.exchange");
        return new Queue("topicQueue1", false, false, false, arguments);
    }*/
}
