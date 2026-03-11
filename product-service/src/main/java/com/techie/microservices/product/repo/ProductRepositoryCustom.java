package com.techie.microservices.product.repo;

import com.techie.microservices.product.model.PageResponse;
import com.techie.microservices.product.model.ProductListResponse;
import com.techie.microservices.product.model.ProductSearchCondition;

public interface ProductRepositoryCustom {

    PageResponse<ProductListResponse> findProductList(
            ProductSearchCondition condition,
            int page,
            int size
    );

}
