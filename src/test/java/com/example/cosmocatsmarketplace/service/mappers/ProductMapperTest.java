package com.example.cosmocatsmarketplace.service.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.example.cosmocatsmarketplace.domain.Category;
import com.example.cosmocatsmarketplace.domain.Product;
import com.example.cosmocatsmarketplace.dto.product.ProductResponseDto;
import com.example.cosmocatsmarketplace.mapper.ProductMapper;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

public class ProductMapperTest {

    private final ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    @Test
    public void testToProductResponseDto() {
        // Given
        UUID id = UUID.randomUUID();
        Category category = new Category(1L, "Test Category");
        Product product = Product.builder()
                .id(id)
                .name("Test Product")
                .description("Test Description")
                .price(100)
                .category(category)
                .build();

        // When
        ProductResponseDto productResponseDto = productMapper.toProductResponseDto(product);

        // Then
        assertNotNull(productResponseDto, "ProductResponseDto should not be null");
        assertEquals(product.getId(), productResponseDto.getId(), "ID should match");
        assertEquals(product.getName(), productResponseDto.getName(), "Name should match");
        assertEquals(product.getDescription(), productResponseDto.getDescription(), "Description should match");
        assertEquals(product.getPrice(), productResponseDto.getPrice(), "Price should match");
        assertEquals(product.getCategory(), productResponseDto.getCategory(), "Category should match");
    }
}
