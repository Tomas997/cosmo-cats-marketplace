package com.example.cosmocatsmarketplace.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_item")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(toBuilder = true)
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_item_id_seq")
    @SequenceGenerator(name = "order_item_id_seq", sequenceName = "order_item_id_seq")
    Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "order_id", nullable = false)
    OrderEntity order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    ProductEntity product;

    Double price;

    Integer quantity;

}
