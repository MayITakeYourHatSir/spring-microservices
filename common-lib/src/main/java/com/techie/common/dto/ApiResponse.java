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
public class ApiResponse<T> {

    private int status;
    private T data;
    private String message;

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    // 成功且有資料
    public static <T> ApiResponse<T> success(T data) {

        return ApiResponse.<T>builder()
                .status(HttpStatus.OK.value())
                .message("成功")
                .data(data)
                .build();
    }

    // 成功但無資料
    public static ApiResponse<Void> success() {

        return ApiResponse.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("成功")
                .build();
    }

    // 自訂成功訊息
    public static <T> ApiResponse<T> success(
            String message,
            T data
    ) {

        return ApiResponse.<T>builder()
                .status(HttpStatus.OK.value())
                .message(message)
                .data(data)
                .build();
    }

    // 失敗
    public static <T> ApiResponse<T> fail(
            HttpStatus status,
            String message
    ) {

        return ApiResponse.<T>builder()
                .status(status.value())
                .message(message)
                .build();
    }

}
