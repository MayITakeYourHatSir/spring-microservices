package com.techie.microservices.order.facade;

import com.techie.microservices.order.client.InventoryClient;
import com.techie.microservices.order.client.dto.StockRequest;
import com.techie.microservices.order.model.OrderItemRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InventoryFacade {

    private final InventoryClient inventoryClient;

    public void reserve(List<OrderItemRequest> items) {

        for (OrderItemRequest item : items) {

            StockRequest request = new StockRequest();

            request.setSkuCode(item.getProductId());
            request.setQty(item.getQuantity());

            inventoryClient.reserve(request);
        }
    }
}
