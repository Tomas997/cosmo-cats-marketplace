package com.example.cosmocatsmarketplace.service;

import com.example.cosmocatsmarketplace.domain.Product;
import com.example.cosmocatsmarketplace.dto.ProductDto;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(UUID productId);

    Product createProduct(ProductDto product);

    Product updateProduct(ProductDto product);

    void deleteProductById(UUID id);
}