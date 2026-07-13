package com.techie.microservices.notification.controller;

import com.techie.microservices.notification.model.MailRequest;
import com.techie.microservices.notification.model.MailResponse;
import com.techie.microservices.notification.service.MailService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mail")
public class MailController {

    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/send")
    public ResponseEntity<MailResponse> sendMail(@Valid @RequestBody MailRequest request) {

        mailService.sendMail(request);

        return ResponseEntity.ok(
                new MailResponse(
                        true,
                        "Mail sent successfully."
                )
        );
    }

}
