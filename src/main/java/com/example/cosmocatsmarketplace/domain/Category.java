package com.example.cosmocatsmarketplace.domain;

import lombok.Builder;
import lombok.Data;



@Data
@Builder
public class Category {
    private long id;
    private String name;
}