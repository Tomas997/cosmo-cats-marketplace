package com.example.cosmocatsmarketplace.dto.order;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class OrderResponseDto {
    String id;
    String consumerName;
    String address;
    String email;
    Double totalPrice;
    String orderStatus;
    List<OrderItemResponseDto> orderItems;
}
