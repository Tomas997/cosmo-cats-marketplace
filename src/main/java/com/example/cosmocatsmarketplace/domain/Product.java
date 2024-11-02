package com.example.cosmocatsmarketplace.domain;


import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Product {

    private UUID id;
    private String name;
    private String description;
    private Integer price;
    private List<Category> categories;
}