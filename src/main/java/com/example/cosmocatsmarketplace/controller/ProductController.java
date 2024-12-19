package com.example.cosmocatsmarketplace.controller;


import com.example.cosmocatsmarketplace.domain.Product;
import com.example.cosmocatsmarketplace.dto.product.ProductCreateDto;
import com.example.cosmocatsmarketplace.dto.product.ProductResponseDto;
import com.example.cosmocatsmarketplace.dto.product.ProductUpdateDto;
import com.example.cosmocatsmarketplace.featuretoggle.FeatureToggles;
import com.example.cosmocatsmarketplace.featuretoggle.FeatureToggle;
import com.example.cosmocatsmarketplace.mapper.ProductMapper;
import com.example.cosmocatsmarketplace.repository.projection.ProductDetailsProjection;
import com.example.cosmocatsmarketplace.service.ProductService;
import com.example.cosmocatsmarketplace.service.exeption.ProductNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products.stream()
                .map(productMapper::toProductResponseDto)
                .toList());
    }

    @FeatureToggle(FeatureToggles.KITTY_PRODUCTS)
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable UUID id) {
        Product product = productService.getProductById(id).orElseThrow(() -> new ProductNotFoundException(id));
        return ResponseEntity.ok(productMapper.toProductResponseDto(product));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDto createProduct(@RequestBody @Valid ProductCreateDto productCreateDto) {
        Product product = productService.createProduct(productCreateDto);
        return productMapper.toProductResponseDto(product);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@RequestBody @Valid ProductUpdateDto productRequestDto, @PathVariable UUID id) {
        Product product = productService.updateProduct(productRequestDto, id);
        return ResponseEntity.ok(productMapper.toProductResponseDto(product));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductById(@PathVariable UUID id) {
        productService.deleteProductById(id);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<ProductDetailsProjection>> getProductsByPriceRange(@RequestParam Integer minPrice, @RequestParam Integer maxPrice) {
        return ResponseEntity.ok(productService.getProductsByPriceRange(minPrice, maxPrice));
    }

}
