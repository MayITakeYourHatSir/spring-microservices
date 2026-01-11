package com.techie.microservices.notification.service;

import com.techie.microservices.order.event.OrderPlacedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final MailService mailService;

    @KafkaListener(topics = "order-placed")
    void listen(OrderPlacedEvent orderPlacedEvent) {
        log.info("Got Message from order-placed topic {}", orderPlacedEvent);
        mailService.sendTextMail(
                "test123@gmail.com",
                "Your order with order number "+ orderPlacedEvent.getOrderNumber() + " is placed successfully",
                "test content");
    }

}
