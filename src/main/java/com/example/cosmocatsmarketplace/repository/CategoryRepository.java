package com.example.cosmocatsmarketplace.repository;

import com.example.cosmocatsmarketplace.repository.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
}