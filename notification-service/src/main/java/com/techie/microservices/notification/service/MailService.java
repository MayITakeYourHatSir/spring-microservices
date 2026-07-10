package com.techie.microservices.notification.service;

import com.techie.common.event.OrderCreatedEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendTextMail(String to, String subject, String content) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);

        mailSender.send(message);
    }

    public void sendOrderCreatedEmail(OrderCreatedEvent event) {

        String content = """
                訂單建立成功

                訂單編號：%s

                金額：%s
                """
                .formatted(
                        event.getOrderNo(),
                        event.getTotalAmount()
                );

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo("lal710609@gmail.com");
        message.setSubject("test mail");
        message.setText(content);

        mailSender.send(message);

        // JavaMailSender
        // Gmail API
        // SendGrid
    }

}
