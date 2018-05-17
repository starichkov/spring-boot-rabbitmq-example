package org.starichkov.java.spring.rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author Vadim Starichkov
 * @since 04.04.2018 18:52
 */
@Component
@RabbitListener(queues = {"rabbit.example"})
public class Receiver {
    @RabbitHandler
    public void receive(String in) {
        System.out.println(" [x] Received '" + in + "'");
    }
}
