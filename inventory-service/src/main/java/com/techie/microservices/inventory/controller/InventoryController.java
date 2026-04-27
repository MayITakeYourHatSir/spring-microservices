package com.techie.microservices.inventory.controller;

import com.techie.common.dto.ApiResponse;
import com.techie.microservices.inventory.model.DeductRequest;
import com.techie.microservices.inventory.model.InventoryResponse;
import com.techie.microservices.inventory.model.StockRequest;
import com.techie.microservices.inventory.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "2.2.1 取得商品庫存", description = "取得商品庫存")
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

    @Operation(summary = "2.2.2 扣除商品庫存", description = "扣除商品庫存")
    @PostMapping("/deduct")
    public ApiResponse deduct(@RequestBody DeductRequest req) {
        inventoryService.deductStock(req.getSkuCode(), req.getQty());

        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("扣庫存成功")
                .timestamp(LocalDateTime.now())
                .build();
    }

    @Operation(summary = "2.2.3 預留商品", description = "預留商品")
    @PostMapping("/reserve")
    public ApiResponse reserve(@RequestBody StockRequest req) {
        inventoryService.reserve(req.getSkuCode(), req.getQty());

        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("預留成功")
                .timestamp(LocalDateTime.now())
                .build();
    }

    @Operation(summary = "2.2.4 釋出預留商品", description = "釋出預留商品")
    @PostMapping("/release")
    public ApiResponse release(@RequestBody StockRequest req) {
        inventoryService.release(req.getSkuCode(), req.getQty());

        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("釋出成功")
                .timestamp(LocalDateTime.now())
                .build();
    }

}
