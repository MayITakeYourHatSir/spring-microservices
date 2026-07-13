package com.techie.microservices.notification.model;

import java.time.LocalDateTime;

public record ErrorResponse(LocalDateTime timestamp,
                            int status,
                            String error,
                            String message) {}
