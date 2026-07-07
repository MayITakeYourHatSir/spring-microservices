package com.techie.microservices.order.model;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class CreateOrderResponse {

    private Long orderId;
    private String orderNo;
    private String status;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
    private List<OrderItemDto> items;

}
