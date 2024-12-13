package com.example.cosmocatsmarketplace.dto.order;

import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Value
@Builder
public class OrderResponseDto {
    UUID id;
    String consumerName;
    String address;
    String email;
    double totalPrice;
    List<OrderItemResponseDto> orderItems;
}
