package com.example.cosmocatsmarketplace.service.impl;

import com.example.cosmocatsmarketplace.domain.Category;
import com.example.cosmocatsmarketplace.domain.Product;
import com.example.cosmocatsmarketplace.dto.ProductDto;
import com.example.cosmocatsmarketplace.service.ProductService;

import java.util.List;

import org.springframework.stereotype.Service;
import java.util.UUID;



@Service
public class ProductServiceImpl implements ProductService {
    private final List<Product> productList = List.of(
            Product.builder()
                    .id(UUID.randomUUID()) // згенеруємо унікальний id
                    .name("Galactic Crystal")
                    .description("A rare crystal found on the surface of Mars.")
                    .price(299)
                    .categories(List.of(
                            new Category(1, "ARTIFACTS"),
                            new Category(2, "EQUIPMENT")
                    ))
                    .build(),
            Product.builder()
                    .id(UUID.randomUUID())
                    .name("Zero-Gravity Boots")
                    .description("Advanced boots designed for optimal movement in zero-gravity environments.")
                    .price(149)
                    .categories(List.of(
                            new Category(2, "EQUIPMENT"),
                            new Category(1, "ARTIFACTS")
                    ))
                    .build(),
            Product.builder()
                    .id(UUID.randomUUID())
                    .name("Lunar Dust Sample")
                    .description("Collected from the surface of the Moon, this dust sample is highly sought by collectors.")
                    .price(499)
                    .categories(List.of(
                            new Category(3, "MATERIALS"),
                            new Category(1, "ARTIFACTS")
                    ))
                    .build()
    );

    @Override
    public List<Product> getAllProducts() {
        return productList;
    }

    @Override
    public Product getProductById(UUID productId) {
        return productList.stream()
                .filter(product -> product.getId().equals(productId))
                .findFirst()
                .orElse(null);
    }


    @Override
    public Product createProduct(ProductDto productDto) {
        Product product = Product.builder()
                .id(UUID.randomUUID())
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .categories(productDto.getCategories())
                .build();
        productList.add(product);
        return  product;
    }

    @Override
    public Product updateProduct(ProductDto productDto) {
        Product product = getProductById(productDto.getId());

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setCategories(productDto.getCategories());
        return product;
    }

    @Override
    public void deleteProductById(UUID id) {
//        productList.remove()
    }
}

