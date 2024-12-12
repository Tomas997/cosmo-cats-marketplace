package com.example.cosmocatsmarketplace.service;

import com.example.cosmocatsmarketplace.domain.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAllCategories();
    Category getCategoryById(Long categoryId);       //optional зробив
    Category create(Category form);
    Category update(Long id, Category categoryDto);
    void deleteById(Long id);
}
