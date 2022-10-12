package io.ymq.rabbitmq.test;

import io.ymq.rabbitmq.run.Startup;
import io.ymq.rabbitmq.ttl.DelayedSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Startup.class)
public class DelayedMessageTest {


    @Autowired
    private DelayedSender delayedSender;

    @Test
    public void test() {
        delayedSender.delayedMessage();
    }
}
