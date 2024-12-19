package com.example.cosmocatsmarketplace.dto.category;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CategoryResponseDto {
    Long id;
    String name;
}
