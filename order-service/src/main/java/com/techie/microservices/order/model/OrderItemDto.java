package com.techie.microservices.order.model;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class OrderItemDto {

    private String productId;
    private String productName;
    private BigDecimal price;
    private Integer quantity;

}
