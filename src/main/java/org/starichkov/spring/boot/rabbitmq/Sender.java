package org.starichkov.spring.boot.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author Vadim Starichkov
 * @since 04.04.2018 18:49
 */
@Component
@Slf4j
public class Sender {
    private final RabbitTemplate template;
    private final String queue;

    @Autowired
    public Sender(RabbitTemplate template, @Value(Constants.CFG_QUEUE_NAME) String queue) {
        this.template = template;
        this.queue = queue;
    }

    @Scheduled(fixedDelay = 5000, initialDelay = 1000)
    public void send() {
        String messageUid = generateUid();
        String message = String.format("Hello World! [uid=%s]", messageUid);
        this.template.convertAndSend(queue, (Object) message, new CorrelationData(messageUid));
        log.info("Message sent: {}", message);
    }

    private static String generateUid() {
        return UUID.randomUUID().toString();
    }
}
