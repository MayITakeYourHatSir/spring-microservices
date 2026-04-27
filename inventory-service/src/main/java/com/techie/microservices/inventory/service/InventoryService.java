package com.techie.microservices.inventory.service;

import com.techie.microservices.inventory.exception.BusinessException;
import com.techie.microservices.inventory.exception.ErrorCode;
import com.techie.microservices.inventory.model.InventoryResponse;
import com.techie.microservices.inventory.repo.InventoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryResponse getBySkuCode(String skuCode) {
        return inventoryRepository.findBySkuCode(skuCode).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "skuCode not found")
        );
    }

    @Transactional
    public void deductStock(String skuCode, int qty) {

        int updated = inventoryRepository.deductStock(skuCode, qty);

        if (updated == 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "此商品庫存不足!");
        }
    }

    @Transactional
    public void reserve(String skuCode, int qty) {

        int updated = inventoryRepository.reserve(skuCode, qty);

        if (updated == 0) {
            validateInventory(skuCode);
            throw new BusinessException(ErrorCode.INSUFFICIENT_STOCK);
        }
    }

    @Transactional
    public void release(String skuCode, int qty) {

        int updated = inventoryRepository.release(skuCode, qty);

        if (updated == 0) {
            validateInventory(skuCode);
            throw new BusinessException(ErrorCode.INVALID_STOCK_OPERATION);
        }
    }

    @Transactional
    public void commit(String skuCode, int qty) {

        int updated = inventoryRepository.commit(skuCode, qty);

        if (updated == 0) {
            validateInventory(skuCode);
            throw new BusinessException(ErrorCode.INVALID_STOCK_OPERATION);
        }
    }

    private void validateInventory(String skuCode) {
        if (!inventoryRepository.existsBySkuCode(skuCode)) {
            throw new BusinessException(ErrorCode.INVENTORY_NOT_FOUND);
        }
    }

}
