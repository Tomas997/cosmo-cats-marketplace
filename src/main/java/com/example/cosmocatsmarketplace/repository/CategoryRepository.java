package com.example.cosmocatsmarketplace.repository;

import com.example.cosmocatsmarketplace.repository.entity.CategoryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<CategoryEntity, Long> {
}