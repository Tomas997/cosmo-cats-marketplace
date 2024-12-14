package com.example.cosmocatsmarketplace.dto.product;

import com.example.cosmocatsmarketplace.dto.category.CategoryResponseDto;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.util.UUID;

@Data
@Value
@Builder
public class ProductResponseDto {
    UUID id;
    String name;
    String description;
    Integer price;
    String status;
    CategoryResponseDto category;
}
