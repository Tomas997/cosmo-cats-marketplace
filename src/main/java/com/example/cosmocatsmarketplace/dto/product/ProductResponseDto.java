package com.example.cosmocatsmarketplace.dto.product;

import com.example.cosmocatsmarketplace.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Data;


import java.util.UUID;

@Data
@AllArgsConstructor
public class ProductResponseDto {
    private UUID id;
    private String name;

    private String description;

    private Integer price;

    private Category category;
}
