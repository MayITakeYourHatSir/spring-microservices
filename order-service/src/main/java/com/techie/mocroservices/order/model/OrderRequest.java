package com.techie.mocroservices.order.model;

import java.math.BigDecimal;

public record OrderRequest(Long id,
                           String skuCode,
                           BigDecimal price,
                           Integer quantity,
                           UserDetails userDetails) {

    public record UserDetails(String email, String name) {}

}
