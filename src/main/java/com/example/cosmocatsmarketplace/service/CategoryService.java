package com.example.cosmocatsmarketplace.service;

import com.example.cosmocatsmarketplace.domain.Category;
import com.example.cosmocatsmarketplace.dto.category.CategoryDto;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> findAllCategories();
    Optional<Category> getCategoryById(Long categoryId);       //optional зробив
    Category create(Category form);
    Category update(Long id, Category categoryDto);
    void deleteById(Long id);
}
