package com.techie.microservices.order.controller;

import com.techie.common.dto.ApiResponse;
import com.techie.microservices.order.model.CreateOrderRequest;
import com.techie.microservices.order.model.CreateOrderResponse;
import com.techie.microservices.order.model.OrderDetailResponse;
import com.techie.microservices.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "2.3.1 新增訂單", description = "新增訂單")
    @PostMapping
    public ResponseEntity<ApiResponse<CreateOrderResponse>> createOrder(
            @RequestBody CreateOrderRequest request,
            @RequestHeader(value = "Idempotency-Key", required = false) String key) {

        CreateOrderResponse createOrderResponse = orderService.createOrder(request, key);

        return ResponseEntity.ok(ApiResponse.success(createOrderResponse));
    }

    @Operation(summary = "2.3.2 查詢訂單", description = "查詢訂單")
    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderDetailResponse>> getOrder(
            @PathVariable Long orderId) {

        OrderDetailResponse response = orderService.getOrder(orderId);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

}
