package com.example.cosmocatsmarketplace.controller;

import com.example.cosmocatsmarketplace.domain.Category;
import com.example.cosmocatsmarketplace.dto.category.CategoryDto;
import com.example.cosmocatsmarketplace.mapper.CategoryMapper;
import com.example.cosmocatsmarketplace.service.CategoryService;
import com.example.cosmocatsmarketplace.service.exeption.CategoryNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/api/v1/categories")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<Category> categories = categoryService.findAllCategories();
        return ResponseEntity.ok(categoryMapper.categoryListToCategoryDtoList(categories));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
        return ResponseEntity.ok(categoryMapper.categoryToCategoryDto(category));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto create(@Valid @RequestBody CategoryDto category) {
        Category categoryModel = categoryMapper.toModel(category);
        return categoryMapper.categoryToCategoryDto(
                categoryService.create(categoryModel)
        );
    }

    @PutMapping("/{id}")
    public CategoryDto update(
            @PathVariable Long id,
            @Valid @RequestBody CategoryDto category
    ) {
        Category categoryModel = categoryMapper.toModel(category);
        return categoryMapper.categoryToCategoryDto(
                categoryService.update(id, categoryModel)
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        try {
            categoryService.deleteById(id);
        } catch (CategoryNotFoundException e) {
        }
    }
}