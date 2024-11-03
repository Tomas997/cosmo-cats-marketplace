package com.example.cosmocatsmarketplace.service.impl;

import com.example.cosmocatsmarketplace.domain.Category;
import com.example.cosmocatsmarketplace.domain.Product;
import com.example.cosmocatsmarketplace.dto.product.ProductCreateDto;
import com.example.cosmocatsmarketplace.dto.product.ProductUpdateDto;
import com.example.cosmocatsmarketplace.service.CategoryService;
import com.example.cosmocatsmarketplace.service.ProductService;

import java.util.ArrayList;
import java.util.List;

import com.example.cosmocatsmarketplace.service.exeption.ProductNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
public class ProductServiceImpl implements ProductService {
    private final CategoryService categoryService;

    private final List<Product> productList = new ArrayList<>();

    public ProductServiceImpl(CategoryService categoryService) {
        this.categoryService = categoryService;
        initializeProductList();
    }

    private void initializeProductList() {
        productList.add(Product.builder()
                .id(UUID.randomUUID())
                .name("Galactic Star Crystal")
                .description("A rare star crystal found on the surface of Mars.")
                .price(299)
                .category(categoryService.findCategoryById(1))
                .build());
        productList.add(Product.builder()
                .id(UUID.randomUUID())
                .name("Zero-Gravity Galaxy Boots")
                .description("Advanced boots designed for optimal movement in galaxy environments.")
                .price(149)
                .category(categoryService.findCategoryById(2))
                .build());
        productList.add(Product.builder()
                .id(UUID.randomUUID())
                .name("Lunar Comet Dust Sample")
                .description("Collected from the surface of the Moon, this comet dust sample is highly sought by collectors.")
                .price(499)
                .category(categoryService.findCategoryById(3))
                .build());
    }



    @Override
    public List<Product> getAllProducts() {
        return productList;
    }

    @Override
    public Optional<Product> getProductById(UUID productId) {
        return productList.stream()
                .filter(product -> product.getId().equals(productId))
                .findFirst();
    }


    @Override
    public Product createProduct(ProductCreateDto productDto) {
        Category category = categoryService.findCategoryById(productDto.getCategory().getId());

        Product product = Product.builder()
                .id(UUID.randomUUID())
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .category(category)
                .build();
        productList.add(product);
        return product;
    }

    @Override
    public Product updateProduct(ProductUpdateDto productDto, UUID id) {
        Product product = getProductById(id).orElseThrow(() -> new ProductNotFoundException(id));

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setCategory(productDto.getCategory());
        return product;
    }

    @Override
    public boolean deleteProductById(UUID id) {
        Optional<Product> productById = getProductById(id);
        productById.ifPresent(productList::remove);
        return productById.isPresent();
    }

}

