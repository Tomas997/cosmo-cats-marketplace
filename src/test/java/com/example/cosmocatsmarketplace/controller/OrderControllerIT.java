package com.example.cosmocatsmarketplace.controller;

import com.example.cosmocatsmarketplace.common.ProductStatus;
import com.example.cosmocatsmarketplace.domain.Order;
import com.example.cosmocatsmarketplace.dto.order.OrderItemRequestDto;
import com.example.cosmocatsmarketplace.dto.order.OrderRequestDto;
import com.example.cosmocatsmarketplace.repository.CategoryRepository;
import com.example.cosmocatsmarketplace.repository.OrderRepository;
import com.example.cosmocatsmarketplace.repository.ProductRepository;
import com.example.cosmocatsmarketplace.repository.entity.CategoryEntity;
import com.example.cosmocatsmarketplace.repository.entity.ProductEntity;
import com.example.cosmocatsmarketplace.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AutoConfigureMockMvc
@SpringBootTest
class OrderControllerIT {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private OrderRepository orderRepository;

    @SpyBean
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        Mockito.reset(orderService);
        productRepository.deleteAll();
        categoryRepository.deleteAll();
        orderRepository.deleteAll();
    }

    @Test
    void testCreateOrder() throws Exception {
        ProductEntity productEntity = productEntity();
        OrderRequestDto orderRequestDto = orderRequestDto(productEntity.getProductReference().toString());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/orders")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(orderRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testCreateOrderProductNotFound() throws Exception {
        OrderRequestDto orderRequestDto = orderRequestDto(UUID.randomUUID().toString());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/orders")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(orderRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    @Test
    void shouldCreateOrderFailed() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/orders")
                .contentType("application/json")
                .content("{}")).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testGetAllOrders() throws Exception {
        ProductEntity product = productEntity();
        orderService.createOrder(orderRequestDto(product.getProductReference().toString()));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/orders")
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    void testDeleteOrder() throws Exception {
        ProductEntity product = productEntity();
        Order order = orderService.createOrder(orderRequestDto(product.getProductReference().toString()));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/orders/" + order.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testDeleteOrderNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/orders/" + UUID.randomUUID().toString()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    private ProductEntity productEntity () {
        return productRepository.save(ProductEntity.builder()
                .category(categoryEntity())
                .productReference(UUID.randomUUID())
                .status(ProductStatus.IN_STOCK)
                .name("Product 1")
                .description("Description 1 ctar")
                .price(14)
                .build());
    }
    private CategoryEntity categoryEntity () {
        return categoryRepository.save(CategoryEntity.builder()
                .name("Category 1")
                .build());
    }

    private OrderRequestDto orderRequestDto (String productId) {
        return OrderRequestDto.builder()
                .orderItems(orderItemRequestDtoList(productId))
                .email("test@example.com")
                .address("address 1")
                .consumerName("Taras").build();
    }
    private List<OrderItemRequestDto> orderItemRequestDtoList (String productId) {
        List<OrderItemRequestDto> orderItemRequestDtoList = new ArrayList<>();

        orderItemRequestDtoList.add(OrderItemRequestDto.builder()
                .productId(productId)
                .quantity(2).build());

        return orderItemRequestDtoList;
    }
}
