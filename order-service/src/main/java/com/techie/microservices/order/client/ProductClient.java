package com.techie.microservices.order.client;

import com.techie.common.dto.ApiResponse;
import com.techie.microservices.order.client.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.List;

@HttpExchange("/api/products")
public interface ProductClient {

    @PostExchange
    ResponseEntity<ApiResponse<PageResponse<ProductListResponse>>> getProducts(
            @RequestBody ProductSearchRequest request,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    );

    @PostExchange("/search-by-skus")
    ResponseEntity<ApiResponse<List<ProductResponse>>> getProductsBySkus(
            @RequestBody ProductSkusRequest request
    );

}
