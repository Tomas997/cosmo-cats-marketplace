package com.example.cosmocatsmarketplace.service.impl;

import com.example.cosmocatsmarketplace.domain.Category;
import com.example.cosmocatsmarketplace.domain.Product;
import com.example.cosmocatsmarketplace.dto.category.CategoryDto;
import com.example.cosmocatsmarketplace.mapper.CategoryMapper;
import com.example.cosmocatsmarketplace.repository.CategoryRepository;
import com.example.cosmocatsmarketplace.repository.entity.CategoryEntity;
import com.example.cosmocatsmarketplace.service.CategoryService;
import com.example.cosmocatsmarketplace.service.exeption.CategoryNotFoundException;
import com.example.cosmocatsmarketplace.service.exeption.ProductNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    public CategoryServiceImpl(CategoryRepository repository, CategoryMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Category> findAllCategories() {
        return mapper.categoryEntityListToCategoryDtoList(
                repository.findAll()
        );
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Category> getCategoryById(Long categoryId) {
        return mapper.toModel(
                repository.findById(categoryId));
    }

    @Transactional
    @Override
    public Category create(Category category) {
        return mapper.toModel(
                repository.save(mapper.toCategoryEntity(category))
        );
    }
    @Transactional
    @Override
    public Category update(Long id, Category categoryDto) {
        Category category = getCategoryById(id).orElseThrow(() -> new CategoryNotFoundException(id));
        category.setName(categoryDto.getName());
        return mapper.toModel(repository.save(mapper.toCategoryEntity(category)));
    }
    @Transactional
    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}