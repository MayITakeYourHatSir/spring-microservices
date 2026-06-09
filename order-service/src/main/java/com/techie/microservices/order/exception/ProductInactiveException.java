package com.techie.microservices.order.exception;

public class ProductInactiveException extends RuntimeException {

    public ProductInactiveException(String productId) {
        super("Product is inactive. productId = " + productId);
    }

    public ProductInactiveException(String productId, Throwable cause) {
        super("Product is inactive. productId = " + productId, cause);
    }
}
