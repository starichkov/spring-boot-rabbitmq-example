package org.starichkov.spring.boot.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author Vadim Starichkov
 * @since 04.04.2018 18:52
 */
@Component
@Slf4j
public class Receiver {

    @RabbitListener(queues = {"rabbit.example"})
    public void receive(Message message) {
        String text = new String(message.getBody());
        log.info("Received message: {}", text);
        if ("warn".equalsIgnoreCase(text)) {
            throw new RuntimeException("Retry");
        } else if ("error".equalsIgnoreCase(text)) {
            throw new AmqpRejectAndDontRequeueException("Do not retry");
        } else {
            log.info("Successfully processed message: {}", text);
        }
    }
}
