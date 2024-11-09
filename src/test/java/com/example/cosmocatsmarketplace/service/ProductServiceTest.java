package com.example.cosmocatsmarketplace.service;

import com.example.cosmocatsmarketplace.domain.Category;
import com.example.cosmocatsmarketplace.domain.Product;
import com.example.cosmocatsmarketplace.dto.product.ProductCreateDto;
import com.example.cosmocatsmarketplace.dto.product.ProductUpdateDto;
import com.example.cosmocatsmarketplace.service.exeption.ProductNotFoundException;
import com.example.cosmocatsmarketplace.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {ProductServiceImpl.class})
class ProductServiceTest {

    @Autowired
    private ProductService productService;
    @MockBean
    private CategoryService mockCategoryService;

    @Test
    void testGetAllProducts() {
        List<Product> products = productService.getAllProducts();

        assertNotNull(products, "Product list should not be null");
        assertEquals(3, products.size(), "Product list size should match the initialized size");
    }

    @Test
    void testGetProductById_ExistingId() {
        UUID existingId = productService.getAllProducts().get(0).getId();

        Optional<Product> product = productService.getProductById(existingId);

        assertTrue(product.isPresent(), "Product should be found for existing ID");
        assertEquals(existingId, product.get().getId(), "Product ID should match");
    }

    @Test
    void testGetProductById_NonExistingId() {
        UUID nonExistingId = UUID.randomUUID();

        Optional<Product> product = productService.getProductById(nonExistingId);

        assertFalse(product.isPresent(), "Product should not be found for non-existing ID");
    }

    @Test
    void testCreateProduct() {
        ProductCreateDto productDto = ProductCreateDto.builder()
                .name("New Product")
                .description("Description of new product")
                .price(200)
                .category(Category.builder().id(1L).name("Galaxy cat toy").build())
                .build();


        Product createdProduct = productService.createProduct(productDto);

        assertNotNull(createdProduct, "Created product should not be null");
        assertEquals(productDto.getName(), createdProduct.getName(), "Product name should match");
        assertEquals(productDto.getDescription(), createdProduct.getDescription(), "Product description should match");
        assertEquals(productDto.getPrice(), createdProduct.getPrice(), "Product price should match");
    }

    @Test
    void testUpdateProduct_ExistingId() {
        UUID existingId = productService.getAllProducts().get(0).getId();
        ProductUpdateDto productDto = ProductUpdateDto.builder()
                .name("Updated Product")
                .description("Updated Description")
                .price(300)
                .category(Category.builder().id(2L).name("Star cats").build())
                .build();


        Product updatedProduct = productService.updateProduct(productDto, existingId);

        assertNotNull(updatedProduct, "Updated product should not be null");
        assertEquals(productDto.getName(), updatedProduct.getName(), "Product name should match updated value");
    }

    @Test
    void testUpdateProduct_NonExistingId() {
        UUID nonExistingId = UUID.randomUUID();
        ProductUpdateDto productDto = ProductUpdateDto.builder().build();

        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(productDto, nonExistingId),
                "ProductNotFoundException should be thrown for non-existing ID");
    }

    @Test
    void testDeleteProductById_ExistingId() {
        UUID existingId = productService.getAllProducts().get(0).getId();

        boolean isDeleted = productService.deleteProductById(existingId);

        assertTrue(isDeleted, "Product should be deleted for existing ID");
        assertFalse(productService.getProductById(existingId).isPresent(), "Deleted product should no longer be found");
    }

    @Test
    void testDeleteProductById_NonExistingId() {
        UUID nonExistingId = UUID.randomUUID();

        boolean isDeleted = productService.deleteProductById(nonExistingId);

        assertFalse(isDeleted, "Product should not be deleted for non-existing ID");
    }


}
