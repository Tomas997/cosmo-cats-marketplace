package com.example.cosmocatsmarketplace.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.cosmocatsmarketplace.domain.Category;
import com.example.cosmocatsmarketplace.domain.Product;
import com.example.cosmocatsmarketplace.dto.product.ProductCreateDto;
import com.example.cosmocatsmarketplace.dto.product.ProductUpdateDto;
import com.example.cosmocatsmarketplace.service.exeption.ProductNotFoundException;
import com.example.cosmocatsmarketplace.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProductServiceTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(categoryService.findCategoryById(1L)).thenReturn(new Category(1L, "Galaxy cat toy"));
        when(categoryService.findCategoryById(2L)).thenReturn(new Category(2L, "Star cats"));
        when(categoryService.findCategoryById(3L)).thenReturn(new Category(3L, "Cosmic Pet Apparel"));
    }

    @Test
    public void testGetAllProducts() {
        List<Product> products = productService.getAllProducts();

        assertNotNull(products, "Product list should not be null");
        assertEquals(3, products.size(), "Product list size should match the initialized size");
    }

    @Test
    public void testGetProductById_ExistingId() {
        UUID existingId = productService.getAllProducts().get(0).getId();

        Optional<Product> product = productService.getProductById(existingId);

        assertTrue(product.isPresent(), "Product should be found for existing ID");
        assertEquals(existingId, product.get().getId(), "Product ID should match");
    }

    @Test
    public void testGetProductById_NonExistingId() {
        UUID nonExistingId = UUID.randomUUID();

        Optional<Product> product = productService.getProductById(nonExistingId);

        assertFalse(product.isPresent(), "Product should not be found for non-existing ID");
    }

    @Test
    public void testCreateProduct() {
        ProductCreateDto productDto = new ProductCreateDto();
        productDto.setName("New Product");
        productDto.setDescription("Description of new product");
        productDto.setPrice(200);
        productDto.setCategory(new Category(1L, "Galaxy cat toy"));

        Product createdProduct = productService.createProduct(productDto);

        assertNotNull(createdProduct, "Created product should not be null");
        assertEquals(productDto.getName(), createdProduct.getName(), "Product name should match");
        assertEquals(productDto.getDescription(), createdProduct.getDescription(), "Product description should match");
        assertEquals(productDto.getPrice(), createdProduct.getPrice(), "Product price should match");
    }

    @Test
    public void testUpdateProduct_ExistingId() {
        UUID existingId = productService.getAllProducts().get(0).getId();
        ProductUpdateDto productDto = new ProductUpdateDto();
        productDto.setName("Updated Product");
        productDto.setDescription("Updated Description");
        productDto.setPrice(300);
        productDto.setCategory(new Category(2L, "Star cats"));

        Product updatedProduct = productService.updateProduct(productDto, existingId);

        assertNotNull(updatedProduct, "Updated product should not be null");
        assertEquals(productDto.getName(), updatedProduct.getName(), "Product name should match updated value");
    }

    @Test
    public void testUpdateProduct_NonExistingId() {
        UUID nonExistingId = UUID.randomUUID();
        ProductUpdateDto productDto = new ProductUpdateDto();

        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(productDto, nonExistingId),
                "ProductNotFoundException should be thrown for non-existing ID");
    }

    @Test
    public void testDeleteProductById_ExistingId() {
        UUID existingId = productService.getAllProducts().get(0).getId();

        boolean isDeleted = productService.deleteProductById(existingId);

        assertTrue(isDeleted, "Product should be deleted for existing ID");
        assertFalse(productService.getProductById(existingId).isPresent(), "Deleted product should no longer be found");
    }

    @Test
    public void testDeleteProductById_NonExistingId() {
        UUID nonExistingId = UUID.randomUUID();

        boolean isDeleted = productService.deleteProductById(nonExistingId);

        assertFalse(isDeleted, "Product should not be deleted for non-existing ID");
    }
}
