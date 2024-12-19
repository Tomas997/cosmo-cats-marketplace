package com.example.cosmocatsmarketplace.repository.entity;

import com.example.cosmocatsmarketplace.common.ProductStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import java.util.UUID;


@Entity
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(toBuilder = true)
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_seq")
    @SequenceGenerator(name = "product_id_seq", sequenceName = "product_id_seq")
    Long id;

    String name;

    String description;

    Integer price;

    @NaturalId
    @Column(nullable = false, unique = true)
    UUID productReference;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "category_id")
    CategoryEntity category;

    @Enumerated(EnumType.STRING)
    ProductStatus status;
}