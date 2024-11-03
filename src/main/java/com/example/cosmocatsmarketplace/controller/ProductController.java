package com.example.cosmocatsmarketplace.controller;


import com.example.cosmocatsmarketplace.domain.Product;
import com.example.cosmocatsmarketplace.dto.product.ProductCreateDto;
import com.example.cosmocatsmarketplace.dto.product.ProductResponseDto;
import com.example.cosmocatsmarketplace.dto.product.ProductUpdateDto;
import com.example.cosmocatsmarketplace.mapper.ProductMapper;
import com.example.cosmocatsmarketplace.service.ProductService;
import com.example.cosmocatsmarketplace.service.exeption.ProductNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products.stream()
                .map(ProductMapper.INSTANCE::toProductResponseDto)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable UUID id) {
        Product product = productService.getProductById(id).orElseThrow(()-> new ProductNotFoundException(id));
        return ResponseEntity.ok(ProductMapper.INSTANCE.toProductResponseDto(product));
    }
    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody @Valid ProductCreateDto productCreateDto) {
        Product product = productService.createProduct(productCreateDto);
        return ResponseEntity.ok(ProductMapper.INSTANCE.toProductResponseDto(product));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@RequestBody @Valid ProductUpdateDto productRequestDto, @PathVariable UUID id) {
        Product product = productService.updateProduct(productRequestDto, id);
        return ResponseEntity.ok(ProductMapper.INSTANCE.toProductResponseDto(product));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductById(@PathVariable UUID id) {
        productService.deleteProductById(id);
    }



}