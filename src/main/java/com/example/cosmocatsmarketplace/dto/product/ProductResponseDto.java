package com.example.cosmocatsmarketplace.dto.product;

import com.example.cosmocatsmarketplace.dto.category.CategoryDto;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Data
@Value
@Builder
public class ProductResponseDto {
    Long id;
    String name;
    String description;
    Integer price;
    String status;
    CategoryDto category;
}
