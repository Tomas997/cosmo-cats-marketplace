package com.example.cosmocatsmarketplace.service;



import com.example.cosmocatsmarketplace.domain.Order;
import com.example.cosmocatsmarketplace.dto.order.OrderRequestDto;
import com.example.cosmocatsmarketplace.dto.order.OrderUpdateDto;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    Order createOrder(OrderRequestDto orderRequestDto);
    Order getOrderById(UUID orderId);
    void deleteOrder(UUID orderId);
    List<Order> getAllOrders();
    Order updateOrder(UUID orderId, OrderUpdateDto orderUpdateDto);
}
