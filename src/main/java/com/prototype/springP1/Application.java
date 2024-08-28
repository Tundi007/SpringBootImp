package com.prototype.springP1;

import com.prototype.springP1.bean.BeansConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class Application
{

    public static final ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(BeansConfig.class);

    public static void main(String[] args)
    {

        context.start();

        SpringApplication.run(Application.class, args);

        context.stop();

        context.registerShutdownHook();

    }

}
