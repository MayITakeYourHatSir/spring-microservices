package com.techie.microservices.order.facade;

import com.techie.common.dto.ApiResponse;
import com.techie.microservices.order.client.ProductClient;
import com.techie.microservices.order.client.dto.ProductIdsRequest;
import com.techie.microservices.order.client.dto.ProductListResponse;
import com.techie.microservices.order.exception.ProductServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductFacade {

    private final ProductClient productClient;

    public List<ProductListResponse> getProductsByIds(
            List<String> productIds
    ) {

        ResponseEntity<ApiResponse<List<ProductListResponse>>> response =
                productClient.getProductsByIds(
                        new ProductIdsRequest(productIds)
                );

        ApiResponse<List<ProductListResponse>> body =
                response.getBody();

        if (body == null || body.getData() == null) {
            throw new ProductServiceException(
                    "Failed to retrieve products"
            );
        }

        return body.getData();
    }

}
