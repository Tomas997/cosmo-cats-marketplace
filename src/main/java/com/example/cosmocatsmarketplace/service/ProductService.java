package com.example.cosmocatsmarketplace.service;

import com.example.cosmocatsmarketplace.domain.Product;
import com.example.cosmocatsmarketplace.dto.product.ProductCreateDto;
import com.example.cosmocatsmarketplace.dto.product.ProductUpdateDto;
import com.example.cosmocatsmarketplace.repository.projection.ProductDetailsProjection;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductService {
    List<Product> getAllProducts();

    Optional<Product> getProductById(UUID productId);

    Product createProduct(ProductCreateDto product);

    Product updateProduct(ProductUpdateDto product, UUID id);

    boolean deleteProductById(UUID id);

    List<ProductDetailsProjection> getProductsByPriceRange(Integer minPrice, Integer maxPrice);
}