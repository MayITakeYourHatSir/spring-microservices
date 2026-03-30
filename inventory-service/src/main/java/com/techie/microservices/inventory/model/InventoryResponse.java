package com.techie.microservices.inventory.model;

public record InventoryResponse(String skuCode,
                                Integer availableStock,
                                Integer reservedStock) {
}
