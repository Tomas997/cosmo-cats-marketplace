package com.example.cosmocatsmarketplace.dto.product;

import com.example.cosmocatsmarketplace.common.ProductStatus;
import com.example.cosmocatsmarketplace.dto.validation.ExtendedValidation;
import com.example.cosmocatsmarketplace.validator.ValidSpaceDescription;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;


@Value
@Builder
@Jacksonized
@GroupSequence({ProductCreateDto.class, ExtendedValidation.class})
public class ProductCreateDto {
    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    String name;
    @NotBlank(message = "Description is mandatory")
    @Size(max = 255, message = "Description cannot exceed 255 characters")
    @ValidSpaceDescription(groups = ExtendedValidation.class)
    String description;
    @NotNull(message = "Price is mandatory")
    @Min(value = 1, message = "Price cannot be 0 or less")
    Integer price;
    @NotNull(message = "Category is mandatory")
    Long categoryId;
    ProductStatus status;
}
