package com.example.cosmocatsmarketplace.service.impl;

import com.example.cosmocatsmarketplace.domain.Category;
import com.example.cosmocatsmarketplace.mapper.CategoryMapper;
import com.example.cosmocatsmarketplace.repository.CategoryRepository;
import com.example.cosmocatsmarketplace.service.CategoryService;
import com.example.cosmocatsmarketplace.service.exeption.CategoryNotFoundException;
import jakarta.persistence.PersistenceException;
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
        return mapper.toCategoryList(
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
        try {
            return mapper.toModel(repository.save(mapper.toCategoryEntity(category)));
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }

    @Transactional
    @Override
    public Category update(Long id, Category categoryDto) {
        Category category = getCategoryById(id).orElseThrow(() -> new CategoryNotFoundException(id));
        category.setName(categoryDto.getName());
        try {
            return mapper.toModel(repository.save(mapper.toCategoryEntity(category)));
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }
}