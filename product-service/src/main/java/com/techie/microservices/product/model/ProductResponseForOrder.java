package com.techie.microservices.product.model;

import java.math.BigDecimal;

public record ProductResponseForOrder(String id,
                                      String name,
                                      String skuCode,
                                      BigDecimal price,
                                      Integer stockQuantity,
                                      Boolean isActive) {
}
