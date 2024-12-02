package com.example.cosmocatsmarketplace.repository;

import com.example.cosmocatsmarketplace.repository.entity.ProductEntity;

import java.util.UUID;

public interface ProductRepository extends NaturalIdRepository<ProductEntity, UUID> {

}