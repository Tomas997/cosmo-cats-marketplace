package com.example.cosmocatsmarketplace.controller;


import com.example.cosmocatsmarketplace.dto.order.OrderRequestDto;
import com.example.cosmocatsmarketplace.dto.order.OrderResponseDto;
import com.example.cosmocatsmarketplace.dto.order.OrderUpdateDto;
import com.example.cosmocatsmarketplace.mapper.OrderMapper;
import com.example.cosmocatsmarketplace.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
@Validated
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    public OrderController(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody @Valid OrderRequestDto orderRequestDto) {
        return ResponseEntity.ok(orderMapper.toOrderResponseDto(orderService.createOrder(orderRequestDto)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getAllOrders() {
        return ResponseEntity.ok(orderMapper.toOrderResponseList(orderService.getAllOrders()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable UUID id) {
        return ResponseEntity.ok(orderMapper.toOrderResponseDto(orderService.getOrderById(id)));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable UUID id) {
        orderService.deleteOrder(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDto> updateOrder(@RequestBody @Valid OrderUpdateDto orderUpdateDto, @PathVariable UUID id) {
        return ResponseEntity.ok(orderMapper.toOrderResponseDto(orderService.updateOrder(id, orderUpdateDto)));
    }

}
