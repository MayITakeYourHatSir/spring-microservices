package com.techie.microservices.order.client.dto;

import lombok.Data;

@Data
public class StockRequest {

    private String skuCode;
    private Integer qty;

}
