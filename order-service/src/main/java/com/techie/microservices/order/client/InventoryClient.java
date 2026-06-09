package com.techie.microservices.order.client;

import com.techie.common.dto.ApiResponse;
import com.techie.microservices.order.client.dto.StockRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

public interface InventoryClient {

    @GetExchange("/api/inventory")
    boolean isInStock(@RequestParam String skuCode, @RequestParam Integer quantity);

    @PostMapping("/reserve")
    ResponseEntity<ApiResponse<Void>> reserve(@RequestBody StockRequest req);

}
