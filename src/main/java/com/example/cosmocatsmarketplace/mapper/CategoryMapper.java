package com.example.cosmocatsmarketplace.mapper;

import com.example.cosmocatsmarketplace.domain.Category;
import com.example.cosmocatsmarketplace.dto.category.CategoryDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto categoryToCategoryDto(Category category);
    List<CategoryDto> categoryListToCategoryDtoList(List<Category> categoryList);
}