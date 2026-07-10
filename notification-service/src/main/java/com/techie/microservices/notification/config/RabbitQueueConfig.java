package com.techie.microservices.notification.config;

import com.techie.common.constant.RabbitMQConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitQueueConfig {

    @Bean
    public Queue emailQueue() {
        return new Queue(RabbitMQConstants.EMAIL_QUEUE);
    }

    @Bean
    public Binding emailBinding(
            Queue emailQueue,
            TopicExchange orderExchange
    ) {
        return BindingBuilder
                .bind(emailQueue)
                .to(orderExchange)
                .with(RabbitMQConstants.ORDER_CREATED_ROUTING_KEY);
    }

}
