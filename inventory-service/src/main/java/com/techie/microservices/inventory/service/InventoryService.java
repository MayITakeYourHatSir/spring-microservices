package com.techie.microservices.inventory.service;

import com.techie.microservices.inventory.model.InventoryResponse;
import com.techie.microservices.inventory.repo.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public boolean isInStock(String skuCode, Integer quantity) {
        return inventoryRepository.existsBySkuCodeAndQuantityIsGreaterThanEqual(skuCode, quantity);
    }

    public InventoryResponse getBySkuCode(String skuCode) {
        return inventoryRepository.findBySkuCode(skuCode).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "skuCode not found")
        );
    }

}
