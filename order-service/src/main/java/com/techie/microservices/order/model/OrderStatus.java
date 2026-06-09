package com.techie.microservices.order.model;

public enum OrderStatus {
    CREATED,
    PENDING_PAYMENT,
    PAID,
    SHIPPED,
    COMPLETED,
    CANCELLED
}
