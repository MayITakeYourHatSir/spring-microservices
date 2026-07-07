package com.techie.microservices.order.model;

import lombok.Getter;

@Getter
public class OrderItemRequest {

    private String skuCode;
    private Integer quantity;

}
