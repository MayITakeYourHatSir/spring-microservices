package com.techie.microservices.order.service;

import com.techie.common.security.SecurityUtil;
import com.techie.microservices.order.client.dto.ProductResponse;
import com.techie.microservices.order.exception.DuplicateOrderException;
import com.techie.microservices.order.exception.OrderNotFoundException;
import com.techie.microservices.order.facade.InventoryFacade;
import com.techie.microservices.order.facade.ProductFacade;
import com.techie.microservices.order.mapper.OrderMapper;
import com.techie.microservices.order.model.*;
import com.techie.microservices.order.repo.OrderRepository;
import com.techie.microservices.order.util.OrderNoGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ProductFacade productFacade;
    private final InventoryFacade inventoryFacade;
    private final OrderNoGenerator orderNoGenerator;
    private final SecurityUtil securityUtil;

    @Transactional
    public CreateOrderResponse createOrder(List<OrderItemRequest> request, String idempotencyKey) {

        validateIdempotency(idempotencyKey);
        List<ProductResponse> products = productFacade.getProductsBySkus(extractProductSkus(request));

        Map<String, ProductResponse> productMap = products.stream()
                .collect(Collectors.toMap(ProductResponse::skuCode, Function.identity()));

        List<OrderItem> orderItems = buildOrderItems(request, productMap);
        BigDecimal totalAmount = calculateTotalAmount(orderItems);
        inventoryFacade.reserve(request);

        Order order = buildOrder(
                orderItems,
                totalAmount,
                idempotencyKey
        );

        orderRepository.save(order);

        return orderMapper.toResponse(order);

    }

    @Transactional
    public OrderDetailResponse getOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new OrderNotFoundException(
                                "Order not found : " + orderId
                        )
                );

        return orderMapper.toDetailResponse(order);
    }

    private void validateIdempotency(String idempotencyKey) {

        if (idempotencyKey == null) {
            return;
        }

        orderRepository.findByIdempotencyKey(idempotencyKey)
                .ifPresent(order -> {
                    throw new DuplicateOrderException(
                            "Order already exists : " + order.getId()
                    );
                });
    }

    private List<String> extractProductSkus(
            List<OrderItemRequest> request
    ) {

        return request.stream()
                .map(OrderItemRequest::getSkuCode)
                .distinct()
                .toList();
    }

    private List<OrderItem> buildOrderItems(
            List<OrderItemRequest> request,
            Map<String, ProductResponse> productMap
    ) {

        return request.stream()
                .map(itemRequest -> {

                    ProductResponse product = productMap.get(itemRequest.getSkuCode());

                    OrderItem item = new OrderItem();

                    item.setProductId(product.id());
                    item.setProductName(product.name());
                    item.setSkuCode(itemRequest.getSkuCode());
                    item.setPrice(product.price());
                    item.setQuantity(itemRequest.getQuantity());

                    return item;
                })
                .toList();
    }

    private BigDecimal calculateTotalAmount(
            List<OrderItem> items
    ) {

        return items.stream()
                .map(item ->
                        item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))
                )
                .reduce(
                        BigDecimal.ZERO,
                        BigDecimal::add
                );
    }

    private Order buildOrder(
            List<OrderItem> orderItems,
            BigDecimal totalAmount,
            String idempotencyKey
    ) {

        Order order = new Order();

        order.setOrderNo(orderNoGenerator.generate());
        order.setUserId(securityUtil.getCurrentUserId());
        order.setStatus(OrderStatus.PENDING_PAYMENT);
        order.setTotalAmount(totalAmount);
        order.setIdempotencyKey(idempotencyKey);
        order.setCreatedAt(LocalDateTime.now());

        order.setItems(orderItems);

        return order;
    }

}
