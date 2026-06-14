package com.techie.microservices.order.service;

import com.techie.microservices.order.client.dto.ProductListResponse;
import com.techie.microservices.order.exception.DuplicateOrderException;
import com.techie.microservices.order.exception.OrderNotFoundException;
import com.techie.microservices.order.exception.ProductInactiveException;
import com.techie.microservices.order.exception.ProductNotFoundException;
import com.techie.microservices.order.facade.InventoryFacade;
import com.techie.microservices.order.facade.ProductFacade;
import com.techie.microservices.order.mapper.OrderMapper;
import com.techie.microservices.order.model.*;
import com.techie.microservices.order.repo.OrderRepository;
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
//    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;
    private final ProductFacade productFacade;
    private final InventoryFacade inventoryFacade;

    @Transactional
    public CreateOrderResponse createOrder(CreateOrderRequest request, String idempotencyKey) {

        validateIdempotency(idempotencyKey);
        List<ProductListResponse> products = productFacade.getProductsByIds(extractProductIds(request));

        Map<String, ProductListResponse> productMap =
                products.stream()
                        .collect(Collectors.toMap(
                                ProductListResponse::id,
                                Function.identity()
                        ));

        validateProducts(request, productMap);
        List<OrderItem> orderItems = buildOrderItems(request, productMap);
        BigDecimal totalAmount = calculateTotalAmount(orderItems);
        inventoryFacade.reserve(request.getItems());

        Order order = buildOrder(
                request,
                orderItems,
                totalAmount,
                idempotencyKey
        );

        orderRepository.save(order);

        return orderMapper.toResponse(order);

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

    private List<String> extractProductIds(
            CreateOrderRequest request
    ) {

        return request.getItems()
                .stream()
                .map(OrderItemRequest::getProductId)
                .distinct()
                .toList();
    }

    private void validateProducts(
            CreateOrderRequest request,
            Map<String, ProductListResponse> productMap
    ) {

        for (OrderItemRequest item : request.getItems()) {

            ProductListResponse product =
                    productMap.get(item.getProductId());

            if (product == null) {
                throw new ProductNotFoundException(
                        item.getProductId()
                );
            }

            if (!product.isActive()) {
                throw new ProductInactiveException(
                        item.getProductId()
                );
            }
        }
    }

    private List<OrderItem> buildOrderItems(
            CreateOrderRequest request,
            Map<String, ProductListResponse> productMap
    ) {

        return request.getItems()
                .stream()
                .map(itemRequest -> {

                    ProductListResponse product =
                            productMap.get(itemRequest.getProductId());

                    OrderItem item = new OrderItem();

                    item.setProductId(product.id());
                    item.setProductName(product.name());
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
                        item.getPrice().multiply(
                                BigDecimal.valueOf(
                                        item.getQuantity()
                                )
                        )
                )
                .reduce(
                        BigDecimal.ZERO,
                        BigDecimal::add
                );
    }

    private Order buildOrder(
            CreateOrderRequest request,
            List<OrderItem> orderItems,
            BigDecimal totalAmount,
            String idempotencyKey
    ) {

        Order order = new Order();

        order.setUserId(request.getUserId());
        order.setStatus(OrderStatus.PENDING_PAYMENT);
        order.setTotalAmount(totalAmount);
        order.setIdempotencyKey(idempotencyKey);
        order.setCreatedAt(LocalDateTime.now());

        order.setItems(orderItems);

        return order;
    }

}
