package com.techie.microservices.inventory.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INVENTORY_NOT_FOUND("I001", "找不到庫存"),
    INSUFFICIENT_STOCK("I002", "庫存不足"),
    INVALID_STOCK_OPERATION("I003", "不合法的操作");

    private final String code;
    private final String message;

}
