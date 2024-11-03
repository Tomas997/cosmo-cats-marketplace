package com.example.cosmocatsmarketplace.dto;

import com.example.cosmocatsmarketplace.domain.Category;
import com.example.cosmocatsmarketplace.validator.ValidCategory;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;


@Data
public class ProductCreateDto {
    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    private String name;
    @NotBlank(message = "Description is mandatory")
    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;
    @NotNull(message = "Price is mandatory")
    @Min(value = 1, message = "Price cannot be 0 or less")
    private Integer price;
    @NotEmpty(message = "Categories are mandatory")
    @ValidCategory
    private List<Category> categories;
}
