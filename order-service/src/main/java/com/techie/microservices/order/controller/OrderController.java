package com.techie.microservices.order.controller;

import com.techie.microservices.order.model.CreateOrderRequest;
import com.techie.microservices.order.model.CreateOrderResponse;
import com.techie.microservices.order.model.OrderRequest;
import com.techie.microservices.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

//    @PostMapping
//    @ResponseStatus(HttpStatus.OK)
//    String placeOrder(@RequestBody OrderRequest orderRequest) {
//        orderService.placeOrder(orderRequest);
//        return "Order Placed Successfully";
//    }

    @PostMapping
    public CreateOrderResponse createOrder(
            @RequestBody CreateOrderRequest request,
            @RequestHeader(value = "Idempotency-Key", required = false) String key) {

        return orderService.createOrder(request, key);
    }

}
