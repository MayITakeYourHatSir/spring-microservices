package com.techie.microservices.notification.service;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import com.techie.common.event.OrderCreatedEvent;
import com.techie.microservices.notification.exception.MailException;
import com.techie.microservices.notification.model.MailRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Properties;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final Gmail gmail;
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public void sendMail(MailRequest request) {

        try{
            Session session = Session.getDefaultInstance(new Properties());

            var outputStream = getByteArrayOutputStream(request, session);

            String encodedEmail = Base64.getUrlEncoder()
                    .encodeToString(
                            outputStream.toByteArray()
                    );

            Message message = new Message();
            message.setRaw(encodedEmail);

            gmail.users()
                    .messages()
                    .send("me", message)
                    .execute();

            log.info("Mail sent successfully. to={}, subject={}, html={}",
                    request.to(), request.subject(), request.isHtml());

        }catch (Exception e){
            log.error("Failed to send mail. to={}", request.to(), e);

            throw new MailException("Failed to send email.", e);
        }

    }

    private static ByteArrayOutputStream getByteArrayOutputStream(MailRequest request, Session session)
            throws MessagingException, IOException {
        MimeMessage mimeMessage = new MimeMessage(session);
        MimeMessageHelper helper = new MimeMessageHelper(
                mimeMessage,
                true,
                StandardCharsets.UTF_8.name()
        );

        helper.setFrom("me");
        helper.setTo(request.to());
        helper.setSubject(request.subject());
        helper.setText(request.body(), request.isHtml());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        mimeMessage.writeTo(outputStream);
        return outputStream;
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
