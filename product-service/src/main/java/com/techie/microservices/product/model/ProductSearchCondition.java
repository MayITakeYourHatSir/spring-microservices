package com.techie.microservices.product.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearchCondition {

    private String id;
    private String name;
    private Boolean isActive;
    private LocalDate createdAt;

}
