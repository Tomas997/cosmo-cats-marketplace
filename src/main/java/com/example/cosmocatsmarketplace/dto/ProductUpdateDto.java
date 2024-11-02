package com.example.cosmocatsmarketplace.dto;

import com.example.cosmocatsmarketplace.domain.Category;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ProductUpdateDto {
    private UUID id;
    private String name;
    private String description;
    private Integer price;
    private List<Category> categories;
}
