package com.techie.microservices.order.client;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("/mail")
public interface NotificationClient {

    @PostMapping("/test")
    String testMail(@RequestParam String to);

}
