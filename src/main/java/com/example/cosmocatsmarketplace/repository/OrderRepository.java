package com.example.cosmocatsmarketplace.repository;

import com.example.cosmocatsmarketplace.repository.entity.OrderEntity;

import java.util.UUID;

public interface OrderRepository extends NaturalIdRepository<OrderEntity, UUID> {
}