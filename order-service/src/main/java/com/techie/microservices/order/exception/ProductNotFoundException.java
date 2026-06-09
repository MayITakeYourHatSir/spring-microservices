package com.techie.microservices.order.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String productId) {
        super("Product not found. productId = " + productId);
    }

    public ProductNotFoundException(String productId, Throwable cause) {
        super("Product not found. productId = " + productId, cause);
    }
}
