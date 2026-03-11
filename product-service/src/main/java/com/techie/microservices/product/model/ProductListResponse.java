package com.techie.microservices.product.model;

import java.time.LocalDate;

public record ProductListResponse(String id,
                                  String name,
                                  Boolean isActive,
                                  LocalDate createdAt) {
}
