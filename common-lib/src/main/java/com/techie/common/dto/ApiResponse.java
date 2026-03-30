package com.techie.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ApiResponse {

    private HttpStatus status;
    private Object data;
    private String message;
    private LocalDateTime timestamp;

}
