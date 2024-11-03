package com.example.cosmocatsmarketplace.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.cosmocatsmarketplace.domain.Category;
import com.example.cosmocatsmarketplace.domain.Product;
import com.example.cosmocatsmarketplace.dto.product.ProductCreateDto;
import com.example.cosmocatsmarketplace.dto.product.ProductResponseDto;
import com.example.cosmocatsmarketplace.dto.product.ProductUpdateDto;
import com.example.cosmocatsmarketplace.mapper.ProductMapper;
import com.example.cosmocatsmarketplace.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.Arrays;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private ProductMapper productMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProducts() throws Exception {
        List<Product> products = Arrays.asList(
                new Product(UUID.randomUUID(), "Product 1", "Description 1", 100, new Category(1L, "Category 1")),
                new Product(UUID.randomUUID(), "Product 2", "Description 2", 200, new Category(2L, "Category 2"))
        );

        List<ProductResponseDto> productResponseDtos = Arrays.asList(
                new ProductResponseDto(products.get(0).getId(), "Product 1", "Description 1", 100, products.get(0).getCategory()),
                new ProductResponseDto(products.get(1).getId(), "Product 2", "Description 2", 200, products.get(1).getCategory())
        );

        when(productService.getAllProducts()).thenReturn(products);
        when(productMapper.toProductResponseDto(products.get(0))).thenReturn(productResponseDtos.get(0));
        when(productMapper.toProductResponseDto(products.get(1))).thenReturn(productResponseDtos.get(1));

        mockMvc.perform(get("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("Product 1"))
                .andExpect(jsonPath("$[1].name").value("Product 2"));
    }

    @Test
    void testGetProductById() throws Exception {
        UUID productId = UUID.randomUUID();
        Product product = new Product(productId, "Product 1", "Description 1", 100, new Category(1L, "Category 1"));
        ProductResponseDto productResponseDto = new ProductResponseDto(productId, "Product 1", "Description 1", 100, product.getCategory());

        when(productService.getProductById(productId)).thenReturn(Optional.of(product));
        when(productMapper.toProductResponseDto(product)).thenReturn(productResponseDto);

        mockMvc.perform(get("/api/v1/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Product 1"))
                .andExpect(jsonPath("$.price").value(100));
    }

    @Test
    public void testCreateProduct() throws Exception {
        ProductCreateDto productCreateDto = new ProductCreateDto("New Product", "planet thing", 150, new Category(1L, "Category 1"));
        Product product = new Product(UUID.randomUUID(), "New Product", "planet thing", 150, new Category(1L, "Category 1"));
        ProductResponseDto productResponseDto = new ProductResponseDto(product.getId(), "New Product", "planet thing", 150, product.getCategory());

        when(productService.createProduct(productCreateDto)).thenReturn(product);
        when(productMapper.toProductResponseDto(product)).thenReturn(productResponseDto);

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productCreateDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("New Product"))  // Change here
                .andExpect(jsonPath("$.price").value(150));
    }



    @Test
    void testUpdateProduct() throws Exception {
        UUID productId = UUID.randomUUID();
        ProductUpdateDto productUpdateDto = new ProductUpdateDto("Updated Product", "planet thing", 200, new Category(2L, "Category 2"));
        Product updatedProduct = new Product(productId, "Updated Product", "planet thing", 200, new Category(2L, "Category 2"));
        ProductResponseDto productResponseDto = new ProductResponseDto(productId, "Updated Product", "planet thing", 200, updatedProduct.getCategory());

        when(productService.updateProduct(productUpdateDto, productId)).thenReturn(updatedProduct);
        when(productMapper.toProductResponseDto(updatedProduct)).thenReturn(productResponseDto);

        mockMvc.perform(put("/api/v1/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Product"))
                .andExpect(jsonPath("$.price").value(200));
    }

    @Test
    void testDeleteProductById() throws Exception {
        UUID productId = UUID.randomUUID();

        when(productService.deleteProductById(productId)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
