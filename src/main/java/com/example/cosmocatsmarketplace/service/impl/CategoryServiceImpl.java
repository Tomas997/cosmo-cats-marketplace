package com.example.cosmocatsmarketplace.service.impl;

import com.example.cosmocatsmarketplace.domain.Category;
import com.example.cosmocatsmarketplace.service.CategoryService;
import com.example.cosmocatsmarketplace.service.exeption.CategoryNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    List<Category> categoryList = new ArrayList<>(List.of(
            Category.builder().id(1L).name("Galaxy cat toy").build(),
            Category.builder().id(2L).name("Star cats").build(),
            Category.builder().id(3L).name("Cosmic Pet Apparel").build()
    ));


    @Override
    public List<Category> findAllCategories() {
        return categoryList;
    }

    @Override
    public Category findCategoryById(long categoryId) {
        return categoryList.stream().filter(category -> category.getId() == categoryId).findFirst().orElseThrow(() -> new CategoryNotFoundException(categoryId));
    }
}