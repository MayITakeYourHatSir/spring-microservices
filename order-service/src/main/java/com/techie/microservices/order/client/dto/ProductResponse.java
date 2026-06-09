package com.techie.microservices.order.client.dto;

public record ProductResponse(String id,
                              String name,
                              String description,
                              Integer price,
                              Integer stockQuantity,
                              Boolean isActive,
                              String createdTime,
                              String updatedTime
                         ) {
}
