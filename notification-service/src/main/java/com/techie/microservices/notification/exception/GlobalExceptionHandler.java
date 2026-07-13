package com.techie.microservices.notification.exception;

import com.techie.microservices.notification.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MailException.class)
    public ResponseEntity<ErrorResponse> handleMailException(MailException e) {

        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                500,
                "MAIL_ERROR",
                e.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);

    }

}
