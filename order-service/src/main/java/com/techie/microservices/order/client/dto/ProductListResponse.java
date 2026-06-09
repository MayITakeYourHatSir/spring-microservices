package com.techie.microservices.order.client.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProductListResponse(String id,
                                  String name,
                                  BigDecimal price,
                                  Boolean isActive,
                                  LocalDate createdAt) {
}
