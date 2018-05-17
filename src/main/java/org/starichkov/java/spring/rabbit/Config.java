package org.starichkov.java.spring.rabbit;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Vadim Starichkov
 * @since 04.04.2018 18:51
 */
@Configuration
public class Config {
    @Bean
    public Queue queue() {
        return new Queue("rabbit.example");
    }
}
