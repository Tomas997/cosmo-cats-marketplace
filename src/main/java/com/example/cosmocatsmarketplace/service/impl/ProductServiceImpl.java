package com.example.cosmocatsmarketplace.service.impl;

import com.example.cosmocatsmarketplace.domain.Product;
import com.example.cosmocatsmarketplace.dto.product.ProductCreateDto;
import com.example.cosmocatsmarketplace.dto.product.ProductUpdateDto;
import com.example.cosmocatsmarketplace.mapper.CategoryMapper;
import com.example.cosmocatsmarketplace.mapper.ProductMapper;
import com.example.cosmocatsmarketplace.repository.ProductRepository;
import com.example.cosmocatsmarketplace.repository.entity.CategoryEntity;
import com.example.cosmocatsmarketplace.repository.entity.ProductEntity;
import com.example.cosmocatsmarketplace.service.CategoryService;
import com.example.cosmocatsmarketplace.service.ProductService;
import com.example.cosmocatsmarketplace.service.exeption.ProductNotFoundException;
import jakarta.persistence.PersistenceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;


    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper, CategoryService categoryService, CategoryMapper categoryMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.categoryService = categoryService;

        this.categoryMapper = categoryMapper;
    }


    @Transactional(readOnly = true)
    @Override
    public List<Product> getAllProducts() {
        return productMapper.toModel(productRepository.findAll());
    }


    @Transactional(readOnly = true)
    @Override
    public Optional<Product> getProductById(UUID productId) {
        return productMapper.toModel(productRepository.findByNaturalId(productId));
    }

    @Transactional
    @Override
    public Product createProduct(ProductCreateDto productDto) {
        CategoryEntity category = categoryMapper.toCategoryEntity(categoryService.getCategoryById(productDto.getCategoryId()));

        Product newProduct = Product.builder()
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .category(categoryMapper.toModel(category))
                .status(productDto.getStatus())
                .build();
        try {
            return productMapper.toModel(productRepository.save(productMapper.toProductEntity(newProduct)));
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }

    @Transactional
    @Override
    public Product updateProduct(ProductUpdateDto productDto, UUID id) {
        ProductEntity product = productRepository.findByNaturalId(id).orElseThrow(() -> new ProductNotFoundException(id));
        CategoryEntity category = categoryMapper.toCategoryEntity(categoryService.getCategoryById(productDto.getCategoryId()));

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setCategory(category);
        product.setStatus(productDto.getStatus());

        try {
            return productMapper.toModel(productRepository.save(product));
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }

    @Transactional
    @Override
    public boolean deleteProductById(UUID id) {
        getProductById(id);

        try {
            productRepository.deleteByNaturalId(id);
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
        return true;
    }

}

