package com.techie.microservices.notification.model;

import jakarta.validation.constraints.Email;

public record MailRequest(@Email String to,
                          String subject,
                          String body, boolean
                          isHtml) {}
