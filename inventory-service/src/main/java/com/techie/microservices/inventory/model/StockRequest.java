package com.techie.microservices.inventory.model;

import lombok.Data;

@Data
public class StockRequest {

    private String skuCode;
    private Integer qty;

}
