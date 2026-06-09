package com.techie.microservices.product.model;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ProductListResponse(String id,
                                  String name,
                                  Boolean isActive,
                                  LocalDateTime createdAt) {
}
