package com.example.cosmocatsmarketplace.mapper;

import com.example.cosmocatsmarketplace.domain.Category;
import com.example.cosmocatsmarketplace.dto.category.CategoryDto;
import com.example.cosmocatsmarketplace.repository.entity.CategoryEntity;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto categoryToCategoryDto(Category category);
    List<CategoryDto> categoryListToCategoryDtoList(List<Category> categoryList);
    List<Category> categoryEntityListToCategoryDtoList(List<CategoryEntity> categoryList);
    Category toModel(CategoryEntity categoryList);
    default Optional<Category> toModel(Optional<CategoryEntity> category) {
        return category.map(this::toModel);
    }
    CategoryEntity toCategoryEntity(Category category);
    Category toModel(CategoryDto categoryDto);
}