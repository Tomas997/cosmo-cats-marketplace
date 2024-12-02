package com.example.cosmocatsmarketplace.service.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.example.cosmocatsmarketplace.domain.Category;
import com.example.cosmocatsmarketplace.domain.Product;
import com.example.cosmocatsmarketplace.dto.product.ProductResponseDto;
import com.example.cosmocatsmarketplace.mapper.ProductMapper;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.UUID;

class ProductMapperTest {

    private final ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    @Test
    void testToProductResponseDto() {
        UUID id = UUID.randomUUID();
        Set<Category> category = Set.of(Category.builder().id(1L).name("Test Category").build());
        Product product = Product.builder()
                .id(id)
                .name("Test Product")
                .description("Test Description")
                .price(100)
                .categories(category)
                .build();

        ProductResponseDto productResponseDto = productMapper.toProductResponseDto(product);

        assertNotNull(productResponseDto, "ProductResponseDto should not be null");
        assertEquals(product.getId(), productResponseDto.getId(), "ID should match");
        assertEquals(product.getName(), productResponseDto.getName(), "Name should match");
        assertEquals(product.getDescription(), productResponseDto.getDescription(), "Description should match");
        assertEquals(product.getPrice(), productResponseDto.getPrice(), "Price should match");
        assertEquals(product.getCategories(), productResponseDto.getCategories(), "Category should match");
    }
}
