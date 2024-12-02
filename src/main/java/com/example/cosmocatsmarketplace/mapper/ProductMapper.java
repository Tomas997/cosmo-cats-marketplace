package com.example.cosmocatsmarketplace.mapper;


import com.example.cosmocatsmarketplace.domain.Product;
import com.example.cosmocatsmarketplace.dto.product.ProductCreateDto;
import com.example.cosmocatsmarketplace.dto.product.ProductResponseDto;
import com.example.cosmocatsmarketplace.repository.entity.ProductEntity;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Optional;


@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResponseDto toProductResponseDto(Product product);

    Product toModel(ProductEntity productEntity);
    List<Product> toModel(List<ProductEntity> productEntities);
    default Optional<Product> toModel(Optional<ProductEntity> productEntity) {
        return productEntity.map(this::toModel);
    }
    ProductEntity toProductEntity(Product product);
    ProductEntity toModel(ProductCreateDto productCreateDto);
}