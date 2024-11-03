package com.example.cosmocatsmarketplace.dto.product;

import com.example.cosmocatsmarketplace.domain.Category;
import lombok.Data;


import java.util.UUID;

@Data
public class ProductResponseDto {
    private UUID id;
    private String name;

    private String description;

    private Integer price;

    private Category category;
}
