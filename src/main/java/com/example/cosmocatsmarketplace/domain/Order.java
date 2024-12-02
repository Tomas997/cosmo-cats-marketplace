package com.example.cosmocatsmarketplace.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class Order {
    UUID id;
    String consumerName;
    String address;
    String email;
    List<OrderItem> orderItems;
    double totalPrice;
}