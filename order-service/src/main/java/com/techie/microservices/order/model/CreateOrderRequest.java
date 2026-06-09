package com.techie.microservices.order.model;

import lombok.Getter;

import java.util.List;

@Getter
public class CreateOrderRequest {

    private Long userId;
    private List<OrderItemRequest> items;

}
