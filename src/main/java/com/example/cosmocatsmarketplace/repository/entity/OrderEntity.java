package com.example.cosmocatsmarketplace.repository.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "\"order\"")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(toBuilder = true)
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_id_seq")
    @SequenceGenerator(name = "order_id_seq", sequenceName = "order_id_seq")
    long id;

    String consumerName;

    String address;

    String email;

    @NaturalId
    @Column(name = "order_reference", unique = true, nullable = false)
    UUID orderReference;

    Double totalPrice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST, orphanRemoval = true)
    List<OrderItemEntity> orderItems;
}

