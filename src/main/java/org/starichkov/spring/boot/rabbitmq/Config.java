package org.starichkov.spring.boot.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author Vadim Starichkov
 * @since 04.04.2018 18:51
 */
@Configuration
@Slf4j
public class Config {

    @Primary
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setConfirmCallback(
                (correlationData, ack, cause) ->
                        log.info("Confirmation received: {}, ack={}, cause={}",
                                correlationData, ack, cause)
        );
        rabbitTemplate.setReturnCallback(
                (message, replyCode, replyText, exchange, routingKey) ->
                        log.info("Return received: {}, replyCode={}, replyText={}, exchange={}, routingKey={}",
                                message, replyCode, replyText, exchange, routingKey)
        );
        return rabbitTemplate;
    }

    @Bean
    public Queue queue(@Value(Constants.CFG_QUEUE_NAME) String queue) {
        return new Queue(queue);
    }
}
