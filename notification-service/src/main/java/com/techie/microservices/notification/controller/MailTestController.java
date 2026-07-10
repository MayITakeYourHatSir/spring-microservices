package com.techie.microservices.notification.controller;

import com.techie.microservices.notification.service.MailService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mail")
public class MailTestController {

    private final MailService mailService;

    public MailTestController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/test")
    public String testMail(@RequestParam String to) {

        mailService.sendTextMail(
                to,
                "Spring Boot SMTP test nail",
                "測試用郵件"
        );

        return "Success!";
    }

}
