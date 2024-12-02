package com.example.cosmocatsmarketplace.service;

import com.example.cosmocatsmarketplace.domain.Category;
import com.example.cosmocatsmarketplace.service.exeption.CategoryNotFoundException;
import com.example.cosmocatsmarketplace.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {CategoryServiceImpl.class})
class CategoryServiceTest {
    @Autowired
    private CategoryService categoryService;

    @Test
    void testFindAllCategories() {
        List<Category> categories = categoryService.findAllCategories();

        assertNotNull(categories, "Categories list should not be null");
        assertEquals(3, categories.size(), "Categories list size should match");
    }

    @Test
    void testGetCategoryById_ExistingId() {
        long categoryId = 1L;

        // Повертаємо Optional<Category> з сервісу
        Optional<Category> category = categoryService.getCategoryById(categoryId);

        // Перевіряємо, що Optional не порожній
        assertTrue(category.isPresent(), "Category should not be empty");

        // Отримуємо категорію з Optional
        Category foundCategory = category.get();

        // Перевіряємо правильність значень
        assertEquals(categoryId, foundCategory.getId(), "Category ID should match");
        assertEquals("Galaxy cat toy", foundCategory.getName(), "Category name should match");
    }


    @Test
    void testGetCategoryById_NonExistingId() {
        long nonExistingId = 99L;

        assertThrows(CategoryNotFoundException.class, () -> categoryService.getCategoryById(nonExistingId),
                "CategoryNotFoundException should be thrown for non-existing ID");
    }
}
