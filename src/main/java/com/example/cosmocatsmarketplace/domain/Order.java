package com.example.cosmocatsmarketplace.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class Order {

    private UUID id;
    private List<Product> products;
}