package com.example.cosmocatsmarketplace.controller;

import com.example.cosmocatsmarketplace.domain.Category;
import com.example.cosmocatsmarketplace.dto.category.CategoryDto;
import com.example.cosmocatsmarketplace.mapper.CategoryMapper;
import com.example.cosmocatsmarketplace.service.CategoryService;
import com.example.cosmocatsmarketplace.service.exeption.CategoryNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class CategoryControllerTestIT {

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
                Category.builder().id(1L).name("Galaxy cat toy").build(),
                Category.builder().id(2L).name("Star cats").build(),
                Category.builder().id(3L).name("Cosmic Pet Apparel").build()
        );
        List<CategoryDto> categoryDtos = Arrays.asList(
                CategoryDto.builder().name("Galaxy cat toy").build(),
                CategoryDto.builder().name("Star cats").build(),
                CategoryDto.builder().name("Cosmic Pet Apparel").build()
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
        Category category = Category.builder().id(1L).name("Galaxy cat toy").build();
        CategoryDto categoryDto = CategoryDto.builder().name("Galaxy cat toy").build();

        when(categoryService.findCategoryById(categoryId)).thenReturn(category);
        when(categoryMapper.categoryToCategoryDto(category)).thenReturn(categoryDto);

        mockMvc.perform(get("/api/v1/categories/{id}", categoryId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Galaxy cat toy"));
    }

    @Test
    void testGetCategoryByIdFail() throws Exception {
        long categoryId = 100L;
        Mockito.when(categoryService.findCategoryById(categoryId)).thenThrow(CategoryNotFoundException.class);

        mockMvc.perform(get("/api/v1/categories/{id}", categoryId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }



}
