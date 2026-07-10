package com.techie.microservices.notification.consumer;

import com.techie.common.constant.RabbitMQConstants;
import com.techie.common.event.OrderCreatedEvent;
import com.techie.microservices.notification.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderCreatedConsumer {

    private final MailService mailService;

    @RabbitListener(queues = RabbitMQConstants.EMAIL_QUEUE)
    public void receive(OrderCreatedEvent event) {

        mailService.sendOrderCreatedEmail(event);

    }

}
