package com.techie.microservices.order.model;

import java.math.BigDecimal;

public record OrderItemResponse(
        String productId,
        String productName,
        BigDecimal price,
        Integer quantity
) {
}
