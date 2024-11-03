package com.example.cosmocatsmarketplace.dto.product;

import com.example.cosmocatsmarketplace.domain.Category;
import com.example.cosmocatsmarketplace.validator.ValidSpaceDescription;
import jakarta.validation.constraints.*;
import lombok.Data;


@Data
public class ProductCreateDto {
    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    private String name;
    @NotBlank(message = "Description is mandatory")
    @Size(max = 255, message = "Description cannot exceed 255 characters")
    @ValidSpaceDescription
    private String description;
    @NotNull(message = "Price is mandatory")
    @Min(value = 1, message = "Price cannot be 0 or less")
    private Integer price;
    @NotNull(message = "Category is mandatory")
    private Category category;
}
