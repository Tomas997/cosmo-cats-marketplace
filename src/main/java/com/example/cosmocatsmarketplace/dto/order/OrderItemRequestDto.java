package com.example.cosmocatsmarketplace.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class OrderItemRequestDto {
    @NotNull(message = "Product name cannot be null")
    String productId;

    @NotNull(message = "Product quantity cannot be null")
    Integer quantity;
}
