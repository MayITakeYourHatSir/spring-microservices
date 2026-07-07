package com.techie.microservices.order.mapper;

import com.techie.microservices.order.model.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {

    public CreateOrderResponse toResponse(Order order) {

        List<OrderItemDto> items = order.getItems().stream()
                .map(this::toDto)
                .toList();

        return CreateOrderResponse.builder()
                .orderId(order.getId())
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

    public OrderDetailResponse toDetailResponse(
            Order order
    ) {

        return new OrderDetailResponse(
                order.getId(),
                order.getUserId(),
                order.getStatus(),
                order.getTotalAmount(),
                order.getCreatedAt(),
                order.getItems()
                        .stream()
                        .map(this::toItemResponse)
                        .toList()
        );
    }

    private OrderItemResponse toItemResponse(OrderItem item) {

        return new OrderItemResponse(
                item.getProductId(),
                item.getProductName(),
                item.getPrice(),
                item.getQuantity()
        );
    }

}
