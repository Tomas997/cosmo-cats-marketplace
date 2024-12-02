package com.example.cosmocatsmarketplace.domain;

import lombok.Data;
import lombok.Value;

@Value
@Data
public class OrderItem {
    String productTitle;
    int quantity;
    double price;
}
