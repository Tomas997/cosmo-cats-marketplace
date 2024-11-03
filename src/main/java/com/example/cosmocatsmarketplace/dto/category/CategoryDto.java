package com.example.cosmocatsmarketplace.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDto {
    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Title cannot exceed 100 characters")
    String name;

}