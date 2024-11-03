package com.example.cosmocatsmarketplace.service;

import com.example.cosmocatsmarketplace.domain.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAllCategories();
    Category findCategoryById(long categoryId);
}
