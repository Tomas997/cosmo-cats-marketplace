package com.example.cosmocatsmarketplace.repository;

import com.example.cosmocatsmarketplace.repository.entity.ProductEntity;
import com.example.cosmocatsmarketplace.repository.projection.ProductDetailsProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends NaturalIdRepository<ProductEntity, UUID> {
    @Query("SELECT p.name AS name, p.description AS description, p.price AS price " +
            "FROM ProductEntity p " +
            "WHERE p.price >= :minPrice AND p.price <= :maxPrice " +
            "ORDER BY name ASC")
    List<ProductDetailsProjection> findProductByPriceRange(
            @Param("minPrice") Integer minPrice,
            @Param("maxPrice") Integer maxPrice);
}