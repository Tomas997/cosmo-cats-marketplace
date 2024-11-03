package com.example.cosmocatsmarketplace.service.mappers;

import com.example.cosmocatsmarketplace.domain.Category;
import com.example.cosmocatsmarketplace.dto.category.CategoryDto;
import com.example.cosmocatsmarketplace.mapper.CategoryMapper;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryMapperTest {

    private final CategoryMapper categoryMapper = Mappers.getMapper(CategoryMapper.class);

    @Test
    public void testCategoryToCategoryDto() {
        Category category = Category.builder()
                .id(1L)
                .name("Test Category")
                .build();

        CategoryDto categoryDto = categoryMapper.categoryToCategoryDto(category);

        assertNotNull(categoryDto, "CategoryDto should not be null");
        assertEquals(category.getName(), categoryDto.getName(), "Name should match");
    }

    @Test
    public void testCategoryListToCategoryDtoList() {
        List<Category> categoryList = Arrays.asList(
                Category.builder().id(1L).name("Category 1").build(),
                Category.builder().id(2L).name("Category 2").build()
        );

        List<CategoryDto> categoryDtoList = categoryMapper.categoryListToCategoryDtoList(categoryList);

        assertNotNull(categoryDtoList, "CategoryDtoList should not be null");
        assertEquals(categoryList.size(), categoryDtoList.size(), "Size of list should match");

        for (int i = 0; i < categoryList.size(); i++) {
            assertEquals(categoryList.get(i).getName(), categoryDtoList.get(i).getName(), "Names should match at index " + i);
        }
    }

    @Test
    void testReturnNullWhenCategoryListIsNull() {
        List<CategoryDto> categoryResult = categoryMapper.categoryListToCategoryDtoList(null);
        assertNull(categoryResult);
    }

    @Test
    void testReturnNullWhenCategoryIsNull() {
        CategoryDto categoryDto = categoryMapper.categoryToCategoryDto(null);
        assertNull(categoryDto);
    }

}
