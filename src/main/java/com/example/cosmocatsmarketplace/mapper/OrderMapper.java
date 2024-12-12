package com.example.cosmocatsmarketplace.mapper;

import com.example.cosmocatsmarketplace.domain.Order;
import com.example.cosmocatsmarketplace.domain.OrderItem;
import com.example.cosmocatsmarketplace.dto.order.OrderResponseDto;
import com.example.cosmocatsmarketplace.repository.entity.OrderEntity;
import com.example.cosmocatsmarketplace.repository.entity.OrderItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "id", source = "orderReference")
    @Mapping(target = "orderItems", source = "orderItems", qualifiedByName = "toOrderItem")
    Order toOrder(OrderEntity orderEntity);

    List<Order> toOrders(List<OrderEntity> orderEntities);

    List<OrderResponseDto> toOrderResponseList(List<Order> orders);

    OrderResponseDto toOrderResponseDto(Order order);

    @Named("toOrderItem")
    default OrderItem toOrderItem(OrderItemEntity orderItem) {
        return new OrderItem(orderItem.getProduct().getName(), orderItem.getQuantity(), orderItem.getPrice());
    }
}
