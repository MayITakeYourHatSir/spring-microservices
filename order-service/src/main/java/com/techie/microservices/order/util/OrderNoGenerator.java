package com.techie.microservices.order.util;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class OrderNoGenerator {

    private static final String PREFIX = "ORD";
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    public String generate() {

        StringBuilder orderNo = new StringBuilder();

        orderNo.append(PREFIX);
        orderNo.append(LocalDateTime.now().format(FORMATTER));

        for (int i = 0; i < 4; i++) {
            orderNo.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }

        return orderNo.toString();
    }

}
