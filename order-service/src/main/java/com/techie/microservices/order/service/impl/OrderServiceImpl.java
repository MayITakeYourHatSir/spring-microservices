package com.techie.microservices.order.service.impl;

import com.techie.microservices.order.client.InventoryClient;
import com.techie.microservices.order.event.OrderPlacedEvent;
import com.techie.microservices.order.model.Order;
import com.techie.microservices.order.model.OrderRequest;
import com.techie.microservices.order.repo.OrderRepository;
import com.techie.microservices.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    @Override
    public void placeOrder(OrderRequest orderRequest) {
        boolean isInStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());

        if(isInStock){
            Order order = new Order();
            order.setId(orderRequest.id());
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setPrice(orderRequest.price());
            order.setSkuCode(orderRequest.skuCode());
            order.setQuantity(orderRequest.quantity());
            orderRepository.save(order);

            // Send the message to Kafka Topic
            OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent(order.getOrderNumber(),
                    orderRequest.userDetails().email());
            kafkaTemplate.send("order-placed", orderPlacedEvent);
        }else {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Product with SkuCode " + orderRequest.skuCode() + " is not in stock");
        }

    }

}
