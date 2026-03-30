package com.techie.microservices.inventory.controller;

import com.techie.common.dto.ApiResponse;
import com.techie.microservices.inventory.model.InventoryResponse;
import com.techie.microservices.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

//    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
//    public boolean isInStock(@RequestParam String skuCode, @RequestParam Integer quantity) {
//        return inventoryService.isInStock(skuCode, quantity);
//    }

    @GetMapping("/{skuCode}")
    public ResponseEntity<ApiResponse> getInventory(@PathVariable String skuCode) {
        InventoryResponse inventoryResponse = inventoryService.getBySkuCode(skuCode);

        ApiResponse response = ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("SUCCESS")
                .data(inventoryResponse)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.ok().body(response);
    }

}
