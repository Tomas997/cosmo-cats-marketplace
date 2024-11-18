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
import com.example.cosmocatsmarketplace.service.exeption.ProductNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.Arrays;

@WebMvcTest(ProductController.class)
class ProductControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private ProductMapper productMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProducts() throws Exception {
        List<Product> products = Arrays.asList(

                Product.builder().id(UUID.randomUUID()).name("Product 1").description("Description 1").price(100).category(Category.builder().id(1L).name("Category 1").build()).build(),
                Product.builder().id(UUID.randomUUID()).name("Product 2").description("Description 2").price(200).category(Category.builder().id(2L).name("Category 2").build()).build()
        );

        List<ProductResponseDto> productResponseDtos = Arrays.asList(
                ProductResponseDto.builder().id(products.get(0).getId()).name("Product 1").description("Description 1").price(100).category(products.get(0).getCategory()).build(),
                ProductResponseDto.builder().id(products.get(1).getId()).name("Product 2").description("Description 2").price(200).category(products.get(1).getCategory()).build()
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

        Product product = Product.builder().id(productId).name("Product 1").description("Description 1").price(100).category(Category.builder().id(1L).name("Category 1").build()).build();
        ProductResponseDto productResponseDto=ProductResponseDto.builder().id(productId).name("Product 1").description("Description 1").price(100).category(product.getCategory()).build();


                when(productService.getProductById(productId)).thenReturn(Optional.of(product));
        when(productMapper.toProductResponseDto(product)).thenReturn(productResponseDto);

        mockMvc.perform(get("/api/v1/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Product 1"))
                .andExpect(jsonPath("$.price").value(100));
    }

    @Test
    void testCreateProduct() throws Exception {
        ProductCreateDto productCreateDto = ProductCreateDto.builder().name("New Product").description("planet thing").price(150).category(Category.builder().id(1L).name("Category 1").build()).build();
        Product product = Product.builder().id(UUID.randomUUID()).name("New Product").description("planet thing").price(150).category(Category.builder().id(1L).name("Category 1").build()).build();
        ProductResponseDto productResponseDto=ProductResponseDto.builder().id(product.getId()).name("New Product").description("planet thing").price(150).category(product.getCategory()).build();

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

        ProductUpdateDto productUpdateDto = ProductUpdateDto.builder().name("Updated Product").description("planet thing").price(200).category(Category.builder().id(2L).name("Category 2").build()).build();
        Product updatedProduct = Product.builder().id(productId).name("Updated Product").description("planet thing").price(200).category(Category.builder().id(2L).name("Category 2").build()).build();
        ProductResponseDto productResponseDto=ProductResponseDto.builder().id(productId).name("Updated Product").description("planet thing").price(200).category(updatedProduct.getCategory()).build();

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

    @Test
    void testGetProductByIdFail() throws Exception {
        UUID productID = UUID.randomUUID();
        Mockito.when(productService.getProductById(productID)).thenThrow(ProductNotFoundException.class);

        mockMvc.perform(get("/api/v1/products/{id}", productID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testCreateProductFail() throws Exception {
        ProductCreateDto productCreateDto = ProductCreateDto.builder().name("Galactic Star Crystal").description( "A rare star crystal found on the surface of Mars.").price(-150).category( Category.builder().id(1L).name( "Galaxy cat toy").build()).build();

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productCreateDto))) // Serialize the object
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


}
