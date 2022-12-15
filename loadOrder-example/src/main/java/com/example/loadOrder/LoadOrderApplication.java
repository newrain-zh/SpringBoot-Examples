package com.example.loadOrder;

import com.example.loadOrder.before.MyApplicationContextInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author newRain
 */
@SpringBootApplication
public class LoadOrderApplication {

    public static void main(String[] args) {
//        SpringApplication.run(LoadOrderApplication.class, args);
        SpringApplication app = new SpringApplication(LoadOrderApplication.class);
        //把MyApplicationContextInitializer注入到容器中
        app.addInitializers(new MyApplicationContextInitializer());
        ConfigurableApplicationContext context = app.run(args);
        context.close();
    }
}
