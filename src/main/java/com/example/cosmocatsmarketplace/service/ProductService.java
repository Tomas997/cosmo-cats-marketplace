package com.example.cosmocatsmarketplace.service;

import com.example.cosmocatsmarketplace.domain.Product;
import com.example.cosmocatsmarketplace.dto.ProductCreateDto;
import com.example.cosmocatsmarketplace.dto.ProductUpdateDto;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(UUID productId);

    Product createProduct(ProductCreateDto product);

    Product updateProduct(ProductUpdateDto product);

    void deleteProductById(UUID id);
}