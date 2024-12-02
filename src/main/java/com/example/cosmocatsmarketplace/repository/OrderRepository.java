package com.example.cosmocatsmarketplace.repository;

import com.example.cosmocatsmarketplace.repository.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}