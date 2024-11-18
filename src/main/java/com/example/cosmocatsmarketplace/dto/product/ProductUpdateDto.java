package com.example.cosmocatsmarketplace.dto.product;

import com.example.cosmocatsmarketplace.domain.Category;
import com.example.cosmocatsmarketplace.dto.validation.ExtendedValidation;
import com.example.cosmocatsmarketplace.validator.ValidSpaceDescription;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;


@Value
@Builder
@Jacksonized
@GroupSequence({ProductUpdateDto.class, ExtendedValidation.class})
public class ProductUpdateDto {
    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    String name;
    @NotBlank(message = "Description is mandatory")
    @Size(max = 255, message = "Description cannot exceed 255 characters")
    @ValidSpaceDescription(groups = ExtendedValidation.class)  //
    String description;
    @NotNull(message = "Price is mandatory")
    @Min(value = 1, message = "Price cannot be 0 or less")
    Integer price;
    @NotNull(message = "Category is mandatory")
    Category category;
}
