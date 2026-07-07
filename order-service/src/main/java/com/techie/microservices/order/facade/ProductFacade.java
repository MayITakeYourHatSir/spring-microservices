package com.techie.microservices.order.facade;

import com.techie.common.dto.ApiResponse;
import com.techie.microservices.order.client.ProductClient;
import com.techie.microservices.order.client.dto.ProductSkusRequest;
import com.techie.microservices.order.client.dto.ProductResponse;
import com.techie.microservices.order.exception.ProductServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductFacade {

    private final ProductClient productClient;

    public List<ProductResponse> getProductsBySkus(List<String> productSkus) {

        ResponseEntity<ApiResponse<List<ProductResponse>>> response =
                productClient.getProductsBySkus(new ProductSkusRequest(productSkus));

        ApiResponse<List<ProductResponse>> body = response.getBody();

        if (body == null || body.getData() == null) {
            throw new ProductServiceException("Failed to retrieve products");
        }

        return body.getData();
    }

}
