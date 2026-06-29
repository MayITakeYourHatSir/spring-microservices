package com.techie.microservices.inventory.controller;

import com.techie.common.dto.ApiResponse;
import com.techie.microservices.inventory.model.DeductRequest;
import com.techie.microservices.inventory.model.InventoryResponse;
import com.techie.microservices.inventory.model.StockRequest;
import com.techie.microservices.inventory.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @Operation(summary = "2.2.1 取得商品庫存", description = "取得商品庫存")
    @GetMapping("/{skuCode}")
    public ResponseEntity<ApiResponse<InventoryResponse>> getInventory(@PathVariable String skuCode) {
        InventoryResponse inventoryResponse = inventoryService.getBySkuCode(skuCode);

        return ResponseEntity.ok(ApiResponse.success(inventoryResponse));
    }

    @Operation(summary = "2.2.2 扣除商品庫存", description = "扣除商品庫存")
    @PostMapping("/deduct")
    public ResponseEntity<ApiResponse<Void>> deduct(@RequestBody DeductRequest req) {
        inventoryService.deductStock(req.getSkuCode(), req.getQty());

        return ResponseEntity.ok(ApiResponse.success());
    }

    @Operation(summary = "2.2.3 預留商品", description = "預留商品")
    @PostMapping("/reserve")
    public ResponseEntity<ApiResponse<Void>> reserve(@RequestBody StockRequest req) {
        inventoryService.reserve(req.getSkuCode(), req.getQty());

        return ResponseEntity.ok(ApiResponse.success());
    }

    @Operation(summary = "2.2.4 釋出預留商品", description = "釋出預留商品")
    @PostMapping("/release")
    public ResponseEntity<ApiResponse<Void>> release(@RequestBody StockRequest req) {
        inventoryService.release(req.getSkuCode(), req.getQty());

        return ResponseEntity.ok(ApiResponse.success());
    }

}
