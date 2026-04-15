package com.techie.microservices.inventory.model;

import lombok.Data;

@Data
public class DeductRequest {

    private String skuCode;
    private Integer qty;

}
