package com.techie.microservices.notification.consumer;

import com.techie.common.constant.RabbitMQConstants;
import com.techie.common.event.OrderCreatedEvent;
import com.techie.microservices.notification.model.MailRequest;
import com.techie.microservices.notification.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderCreatedConsumer {

    private final MailService mailService;

    @RabbitListener(queues = RabbitMQConstants.EMAIL_QUEUE)
    public void receive(OrderCreatedEvent event) {

        log.info("Receive order created event : {}", event.getOrderNo());

        String subject = "訂單建立成功";

        String content = """
                訂單建立成功

                訂單編號：%s

                金額：%s
                """
                .formatted(
                        event.getOrderNo(),
                        event.getTotalAmount()
                );


        mailService.sendMail(
                new MailRequest(
                        event.getEmail(),
                        subject,
                        content,
                        false
                )
        );

    }

}
