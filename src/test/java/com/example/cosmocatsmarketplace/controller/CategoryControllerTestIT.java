package com.example.cosmocatsmarketplace.controller;

import com.example.cosmocatsmarketplace.AbstractIt;
import com.example.cosmocatsmarketplace.dto.category.CategoryDto;
import com.example.cosmocatsmarketplace.repository.CategoryRepository;
import com.example.cosmocatsmarketplace.repository.entity.CategoryEntity;
import com.example.cosmocatsmarketplace.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
@SpringBootTest
class CategoryControllerTestIT extends AbstractIt {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryRepository categoryRepository;

    @SpyBean
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        Mockito.reset(categoryService);
        categoryRepository.deleteAll();
    }


    @Test
    void testGetAllCategories() throws Exception {
        saveCategoryEntity();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/categories")).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetCategoryById() throws Exception {
        CategoryEntity category = saveCategoryEntity();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/categories/" + category.getId())).andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    void testGetCategoryByIdFail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/categories/" + 1L)).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testCreateCategory() throws Exception {
        CategoryDto categoryDto = createCategoryDto();

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(categoryDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void testCreateCategoryFailed() throws Exception {
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"));

        response.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testUpdateCategory() throws Exception {
        CategoryEntity categoryEntity = saveCategoryEntity();
        CategoryDto categoryDto = createCategoryDto();
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/categories/" + categoryEntity.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(categoryDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    void testUpdateCategoryFailed() throws Exception {
        CategoryEntity categoryEntity = saveCategoryEntity();
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/categories/" + categoryEntity.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"));

        response.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testNotFoundUpdateCategory() throws Exception {
        CategoryDto categoryDto = createCategoryDto();
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/categories/" + 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryDto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testDeleteCategory() throws Exception {
        CategoryEntity categoryEntity = saveCategoryEntity();
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/categories/" + categoryEntity.getId()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testDeleteCategoryNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/categories/{id}", 500L))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    private CategoryDto createCategoryDto() {
        return CategoryDto.builder().name("Test Category updated").build();
    }
    private CategoryEntity saveCategoryEntity() {
        return categoryRepository.save(CategoryEntity.builder()
                .name("Test category")
                .build());
    }
}
