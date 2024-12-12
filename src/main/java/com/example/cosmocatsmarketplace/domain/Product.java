package com.example.cosmocatsmarketplace.domain;

import com.example.cosmocatsmarketplace.common.ProductStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {

    private Long id;
    private String name;
    private String description;
    private Integer price;
    private ProductStatus status;
    private Category category;
}