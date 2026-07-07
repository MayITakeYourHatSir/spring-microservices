package com.techie.microservices.order.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String skuCode) {
        super("Product not found. skuCode = " + skuCode);
    }

    public ProductNotFoundException(String skuCode, Throwable cause) {
        super("Product not found. skuCode = " + skuCode, cause);
    }
}
