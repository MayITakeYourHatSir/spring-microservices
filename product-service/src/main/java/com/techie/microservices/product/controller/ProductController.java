package com.techie.microservices.product.controller;

import com.techie.microservices.product.model.*;
import com.techie.microservices.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "2.1.1 新增商品", description = "新增商品")
    @PostMapping
    ResponseEntity<ApiResponse> createProduct(@RequestBody ProductRequest request){
        String productId = productService.createProduct(request);

        ApiResponse response = ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("成功")
                .data(productId)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "2.1.2 依商品 ID 取得商品資訊", description = "透過商品 ID 查詢單一商品的詳細資訊")
    @GetMapping("/{id}")
    ResponseEntity<ApiResponse> getProduct(@PathVariable String id){
        ProductResponse product = productService.getProductById(id);

        ApiResponse response = ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("成功")
                .data(product)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "2.1.3 依商品 ID 修改商品資訊", description = "透過商品 ID 修改單一商品的資訊")
    @PutMapping("/{id}")
    ResponseEntity<ApiResponse> updateProduct(@PathVariable String id,
                                              @RequestBody ProductRequest request){
        productService.updateProduct(id, request);

        ApiResponse response = ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("成功")
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "2.1.4 依商品 ID 刪除商品", description = "透過商品 ID 刪除單一商品")
    @DeleteMapping("/{id}")
    ResponseEntity<ApiResponse> deleteProduct(@PathVariable String id){
        productService.deleteProduct(id);

        ApiResponse response = ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("成功")
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.ok().body(response);
    }

    @Operation(
            summary = "2.1.5 依搜尋條件取得商品列表",
            description = "依搜尋條件取得對應商品並限制資料筆數，條件包含商品 id、名字、是否上架以及上架日期")
    @PostMapping("/list")
    ResponseEntity<ApiResponse> getProducts(
            @RequestBody ProductSearchRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        PageResponse<ProductListResponse> pageResponse = productService.getProductList(request, page, size);

        ApiResponse response = ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("成功")
                .data(pageResponse)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.ok().body(response);
    }

}
