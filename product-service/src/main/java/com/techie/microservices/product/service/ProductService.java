package com.techie.microservices.product.service;

import com.techie.microservices.product.model.*;
import com.techie.microservices.product.repo.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

        return productRepository.findProductList(condition, page, size);
    }

    @Transactional(readOnly = true)
    public List<ProductResponseForOrder> getProductsBySkus(List<String> productSkus) {

        List<Product> products = productRepository.findBySkuCodeIn(productSkus);

        return products.stream()
                .map(this::convertToProductResponse)
                .toList();
    }

    private ProductResponse convertToProductDto(Product product){
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice().intValue(),
                product.getStockQuantity(),
                product.getIsActive(),
                product.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                product.getUpdatedAt() == null ? null :
                        product.getUpdatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        );
    }

    private ProductResponseForOrder convertToProductResponse(Product product) {

        return new ProductResponseForOrder(
                product.getId(),
                product.getName(),
                product.getSkuCode(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getIsActive()
        );
    }

}
