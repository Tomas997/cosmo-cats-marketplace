package com.example.cosmocatsmarketplace.dto.order;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder(toBuilder = true)
public class OrderRequestDto {
    @NotNull(message = "Order items cannot be null")
    List<OrderItemRequestDto> orderItems;

    @NotNull(message = "Address cannot be null")
    @Size(max = 255, message = "Address cannot exceed 255 characters")
    String address;

    @Email(message = "Email should be valid")
    String email;

    @NotNull(message = "Name cannot be null")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    String consumerName;
}
