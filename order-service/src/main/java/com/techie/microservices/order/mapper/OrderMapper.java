package com.techie.microservices.order.mapper;

import com.techie.microservices.order.model.CreateOrderResponse;
import com.techie.microservices.order.model.Order;
import com.techie.microservices.order.model.OrderItem;
import com.techie.microservices.order.model.OrderItemDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {

    public CreateOrderResponse toResponse(Order order) {

        List<OrderItemDto> items = order.getItems().stream()
                .map(this::toDto)
                .toList();

        return CreateOrderResponse.builder()
                .orderNo(order.getOrderNo())
                .status(order.getStatus().name())
                .totalAmount(order.getTotalAmount())
                .createdAt(order.getCreatedAt())
                .items(items)
                .build();
    }

    private OrderItemDto toDto(OrderItem item) {
        return OrderItemDto.builder()
                .productId(item.getProductId())
                .productName(item.getProductName())
                .price(item.getPrice())
                .quantity(item.getQuantity())
                .build();
    }

}
