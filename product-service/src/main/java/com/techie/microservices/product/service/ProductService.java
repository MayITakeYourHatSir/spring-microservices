package com.techie.microservices.product.service;

import com.techie.microservices.product.model.*;
import com.techie.microservices.product.repo.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public String createProduct(ProductRequest request) {
        Product product = Product.builder()
                .name(request.name())
                .description(request.description())
                .stockQuantity(request.stockQuantity())
                .price(BigDecimal.valueOf(request.price()))
                .isActive(true)
                .build();

        return productRepository.save(product).getId();
    }

    public ProductResponse getProductById(String id) {
        return convertToProductDto(productRepository.findById(id).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "找不到 id="+id+" 的產品")
                )
        );
    }

    public void updateProduct(String id, ProductRequest request) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "找不到 id="+id+" 的產品")
        );

        if(request.name() != null) product.setName(request.name());
        if(request.description() != null) product.setDescription(request.description());
        if(request.price() != null) product.setPrice(BigDecimal.valueOf(request.price()));
        if(request.stockQuantity() != null) product.setStockQuantity(request.stockQuantity());
        product.setUpdatedAt(LocalDate.now());
        productRepository.save(product);
    }

    public void deleteProduct(String id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "找不到 id="+id+" 的產品")
        );

        productRepository.delete(product);
    }

    public PageResponse<ProductListResponse> getProductList(
            ProductSearchRequest request,
            int page,
            int size
    ) {
        ProductSearchCondition condition = ProductSearchCondition.builder()
                .id(request.getId())
                .name(request.getName())
                .isActive(request.getIsActive())
                .createdAt(request.getCreatedAt())
                .build();

        PageResponse<ProductListResponse> pageResponse = productRepository.findProductList(condition, page, size);
        System.out.println("pageResponse: "+pageResponse);

        return productRepository.findProductList(condition, page, size);
    }

    private ProductResponse convertToProductDto(Product product){
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice().intValue(),
                product.getStockQuantity(),
                product.getIsActive(),
                product.getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE),
                product.getUpdatedAt() == null ? null :
                        product.getUpdatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE)
        );
    }

}
