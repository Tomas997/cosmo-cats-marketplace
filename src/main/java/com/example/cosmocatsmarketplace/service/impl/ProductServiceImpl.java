package com.example.cosmocatsmarketplace.service.impl;

import com.example.cosmocatsmarketplace.domain.Product;
import com.example.cosmocatsmarketplace.dto.product.ProductCreateDto;
import com.example.cosmocatsmarketplace.dto.product.ProductUpdateDto;
import com.example.cosmocatsmarketplace.mapper.ProductMapper;
import com.example.cosmocatsmarketplace.repository.ProductRepository;
import com.example.cosmocatsmarketplace.service.CategoryService;
import com.example.cosmocatsmarketplace.service.ProductService;
import com.example.cosmocatsmarketplace.service.exeption.ProductNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;


    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository=productRepository;
        this.productMapper = productMapper;
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
        return productMapper.toModel(productRepository.save(productMapper.toModel(productDto)));
    }

    @Transactional
    @Override
    public Product updateProduct(ProductUpdateDto productDto, UUID id) {
        Product product = getProductById(id).orElseThrow(() -> new ProductNotFoundException(id));

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setCategories(productDto.getCategories());
        return productMapper.toModel(productRepository.save(productMapper.toProductEntity(product)));
    }
    @Transactional
    @Override
    public boolean deleteProductById(UUID id) {
        Optional<Product> productById = getProductById(id);
        if (productById.isPresent()) {
            productRepository.deleteByNaturalId(id);
        }
        return productById.isPresent();
    }

}

