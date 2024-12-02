package com.example.cosmocatsmarketplace.repository.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "category")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_category_id")
    @SequenceGenerator(name = "seq_category_id", sequenceName = "seq_category_id")
    Long id;

    @Column(unique = true, nullable = false)
    String name;
}
