package com.example.cosmocatsmarketplace.controller;


import com.example.cosmocatsmarketplace.AbstractIt;
import com.example.cosmocatsmarketplace.common.ProductStatus;
import com.example.cosmocatsmarketplace.dto.product.ProductCreateDto;
import com.example.cosmocatsmarketplace.dto.product.ProductUpdateDto;
import com.example.cosmocatsmarketplace.repository.CategoryRepository;
import com.example.cosmocatsmarketplace.repository.ProductRepository;
import com.example.cosmocatsmarketplace.repository.entity.CategoryEntity;
import com.example.cosmocatsmarketplace.repository.entity.ProductEntity;
import com.example.cosmocatsmarketplace.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

//@WebMvcTest(ProductController.class)
@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTestIT extends AbstractIt {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @SpyBean
    private ProductService productService;


    @BeforeEach
    void setUp() {
        Mockito.reset(productService);
        productRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @WithMockUser
    @Test
    void testGetAllProduct() throws Exception {
        createProduct();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithMockUser
    @Test
    void testGetProductById() throws Exception {
        ProductEntity product = createProduct();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/" + product.getProductReference()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithMockUser
    @Test
    void testGetProductByIdNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/" + UUID.randomUUID()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void testCreateProduct() throws Exception {
        CategoryEntity categoryEntity = createCategory();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(productCreateDto(categoryEntity.getId()))))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @WithMockUser
    @Test
    void testCreateProductFailed() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products")
                        .contentType("application/json")
                        .content("{}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void testUpdateProduct() throws Exception {
        ProductEntity product = createProduct();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/products/" + product.getProductReference())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(productUpdateDto(product.getCategory().getId()))))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithMockUser
    @Test
    void testUpdateProductFailed() throws Exception {
        ProductEntity product = createProduct();
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/products/" + product.getProductReference())
                .contentType("application/json")
                .content("{}")).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void testUpdateProductNotFound() throws Exception {
        CategoryEntity category = createCategory();
        ProductUpdateDto productUpdateDto = productUpdateDto(category.getId());

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/products/" + UUID.randomUUID())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(productUpdateDto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @WithMockUser
    @Test
    void testGetProductByPriceRange() throws Exception {
        createProduct();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/filter")
                        .param("minPrice", String.valueOf(10))
                        .param("maxPrice", String.valueOf(20))
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @WithMockUser
    @Test
    void testGetProductByPriceRangeFailed() throws Exception {
        createProduct();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/filter")
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @WithMockUser(roles = "ADMIN")
    @Test
    void testDeleteProduct() throws Exception {
        ProductEntity product = productRepository.save(ProductEntity.builder()
                .name("Test Product for deleting galaxy")
                .price(15)
                .productReference(UUID.randomUUID())
                .description("Test product description")
                .status(ProductStatus.IN_STOCK)
                .category(createCategory())
                .build());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/products/" + product.getProductReference()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void testDeleteProductNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/products/" + UUID.fromString("37e0d1a8-a692-450e-81c6-29e8f56e8a6e")))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    private CategoryEntity createCategory() {
        return  categoryRepository.save(CategoryEntity.builder()
                .name("Test Category for product")
                .build());
    }

    private ProductEntity createProduct() {
        return productRepository.save(ProductEntity.builder()
                .name("Test Product galaxy")
                .price(15)
                .productReference(UUID.fromString("37e0d1a8-a692-450e-81c6-29e8f56e8a6a"))
                .description("Test product description star")
                .status(ProductStatus.IN_STOCK)
                .category(createCategory())
                .build());
    }

    private ProductCreateDto productCreateDto(long categoryId) {
        return ProductCreateDto.builder()
                .name("Product from productCreateDto")
                .description("Product Description star")
                .categoryId(categoryId)
                .price(14)
                .status(ProductStatus.IN_STOCK)
                .build();
    }
    private ProductUpdateDto productUpdateDto(long categoryId) {
        return ProductUpdateDto.builder()
                .name("Product from productUpdateDto")
                .description("Product Description star")
                .categoryId(categoryId)
                .price(14)
                .status(ProductStatus.IN_STOCK)
                .build();
    }
}
