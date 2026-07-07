package com.techie.microservices.order.client.dto;

import java.math.BigDecimal;

public record ProductResponse(String id,
                              String name,
                              String skuCode,
                              BigDecimal price,
                              Integer stockQuantity,
                              Boolean isActive) {
}
