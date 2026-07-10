package com.techie.microservices.order.publisher;

import com.techie.common.config.RabbitMQConfig;
import com.techie.common.constant.RabbitMQConstants;
import com.techie.common.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publish(OrderCreatedEvent event) {

        rabbitTemplate.convertAndSend(
                RabbitMQConstants.ORDER_EXCHANGE,
                RabbitMQConstants.ORDER_CREATED_ROUTING_KEY,
                event
        );

    }

}
