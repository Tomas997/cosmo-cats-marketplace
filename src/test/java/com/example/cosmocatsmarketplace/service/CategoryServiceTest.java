package com.example.cosmocatsmarketplace.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.cosmocatsmarketplace.domain.Category;
import com.example.cosmocatsmarketplace.service.exeption.CategoryNotFoundException;
import com.example.cosmocatsmarketplace.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CategoryServiceTest {

    private CategoryServiceImpl categoryService;

    @BeforeEach
    public void setUp() {
        categoryService = new CategoryServiceImpl();
    }

    @Test
    public void testFindAllCategories() {
        List<Category> categories = categoryService.findAllCategories();

        assertNotNull(categories, "Categories list should not be null");
        assertEquals(3, categories.size(), "Categories list size should match");
    }

    @Test
    public void testFindCategoryById_ExistingId() {
        long categoryId = 1L;

        Category category = categoryService.findCategoryById(categoryId);

        assertNotNull(category, "Category should not be null");
        assertEquals(categoryId, category.getId(), "Category ID should match");
        assertEquals("Galaxy cat toy", category.getName(), "Category name should match");
    }

    @Test
    public void testFindCategoryById_NonExistingId() {
        long nonExistingId = 99L;

        assertThrows(CategoryNotFoundException.class, () -> categoryService.findCategoryById(nonExistingId),
                "CategoryNotFoundException should be thrown for non-existing ID");
    }
}
