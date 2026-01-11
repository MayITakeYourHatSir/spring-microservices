package com.techie.microservices.order.service;

import com.techie.microservices.order.model.OrderRequest;

public interface OrderService {

    void placeOrder(OrderRequest orderRequest);

}
