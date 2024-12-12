package com.example.cosmocatsmarketplace.service.impl;

import com.example.cosmocatsmarketplace.dto.order.OrderRequestDto;
import com.example.cosmocatsmarketplace.dto.order.OrderResponseDto;
import com.example.cosmocatsmarketplace.common.ProductStatus;
import com.example.cosmocatsmarketplace.domain.Order;
import com.example.cosmocatsmarketplace.dto.order.OrderItemRequestDto;
import com.example.cosmocatsmarketplace.mapper.OrderMapper;
import com.example.cosmocatsmarketplace.repository.OrderRepository;
import com.example.cosmocatsmarketplace.repository.ProductRepository;
import com.example.cosmocatsmarketplace.repository.entity.OrderEntity;
import com.example.cosmocatsmarketplace.repository.entity.OrderItemEntity;
import com.example.cosmocatsmarketplace.repository.entity.ProductEntity;
import com.example.cosmocatsmarketplace.service.OrderService;
import com.example.cosmocatsmarketplace.service.exeption.OrderNotFoundException;
import com.example.cosmocatsmarketplace.service.exeption.ProductNotFoundException;
import com.example.cosmocatsmarketplace.service.exeption.ProductStatusIsInCorrectException;
import jakarta.persistence.PersistenceException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ProductRepository productRepository;

    @Transactional
    @Override
    public Order createOrder(OrderRequestDto orderRequestDto) {
        try {
            List<OrderItemEntity> orderItems = new ArrayList<>();
            Double totalPrice = 0.0;

            for (OrderItemRequestDto orderItem: orderRequestDto.getOrderItems()) {
                ProductEntity product = productRepository.findByNaturalId(UUID.fromString(orderItem.getProductId()))
                        .orElseThrow(() -> new ProductNotFoundException(UUID.fromString(orderItem.getProductId())));

                if (product.getStatus().equals(ProductStatus.OUT_OF_STOCK) || product.getStatus().equals(ProductStatus.DISCONTINUED)) {
                    throw new ProductStatusIsInCorrectException(product.getStatus().toString());
                }

                OrderItemEntity orderItemEntity = OrderItemEntity.builder()
                        .product(product)
                        .price(product.getPrice() * orderItem.getQuantity())
                        .quantity(orderItem.getQuantity())
                        .build();
                orderItems.add(orderItemEntity);
                totalPrice += orderItemEntity.getPrice();
            }

            OrderEntity order = OrderEntity.builder()
                    .orderItems(orderItems)
                    .orderReference(UUID.randomUUID())
                    .email(orderRequestDto.getEmail())
                    .consumerName(orderRequestDto.getConsumerName())
                    .address(orderRequestDto.getAddress())
                    .totalPrice(totalPrice)
                    .build();

            orderItems.forEach(orderItem -> orderItem.setOrder(order));

            return orderMapper.toOrder(orderRepository.save(order));
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Order getOrderById(UUID orderId) {
        OrderEntity order = orderRepository.findByNaturalId(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId.toString()));

        return orderMapper.toOrder(order);
    }

    @Override
    @Transactional
    public void deleteOrder(UUID orderId) {
        getOrderById(orderId);

        try {
            orderRepository.deleteByNaturalId(orderId);
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return orderMapper.toOrders(orderRepository.findAll());
    }

//todo projection


}
