package com.example.cosmocatsmarketplace.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.cosmocatsmarketplace.domain.Category;
import com.example.cosmocatsmarketplace.dto.category.CategoryDto;
import com.example.cosmocatsmarketplace.mapper.CategoryMapper;
import com.example.cosmocatsmarketplace.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Arrays;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private CategoryMapper categoryMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCategories() throws Exception {
        List<Category> categories = Arrays.asList(
                new Category(1L, "Galaxy cat toy"),
                new Category(2L, "Star cats"),
                new Category(3L, "Cosmic Pet Apparel")
        );
        List<CategoryDto> categoryDtos = Arrays.asList(
                new CategoryDto("Galaxy cat toy"),
                new CategoryDto("Star cats"),
                new CategoryDto("Cosmic Pet Apparel")
        );

        when(categoryService.findAllCategories()).thenReturn(categories);
        when(categoryMapper.categoryListToCategoryDtoList(categories)).thenReturn(categoryDtos);

        mockMvc.perform(get("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3))
                .andExpect(jsonPath("$[0].name").value("Galaxy cat toy"))
                .andExpect(jsonPath("$[1].name").value("Star cats"))
                .andExpect(jsonPath("$[2].name").value("Cosmic Pet Apparel"));
    }

    @Test
    void testGetCategoryById() throws Exception {
        long categoryId = 1L;
        Category category = new Category(categoryId, "Galaxy cat toy");
        CategoryDto categoryDto = new CategoryDto("Galaxy cat toy");

        when(categoryService.findCategoryById(categoryId)).thenReturn(category);
        when(categoryMapper.categoryToCategoryDto(category)).thenReturn(categoryDto);

        mockMvc.perform(get("/api/v1/categories/{id}", categoryId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Galaxy cat toy"));
    }
}
