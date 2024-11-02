package com.example.cosmocatsmarketplace.mapper;


import com.example.cosmocatsmarketplace.domain.Product;
import com.example.cosmocatsmarketplace.dto.ProductResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
    ProductResponseDto toProductResponseDto(Product product);
    List<ProductResponseDto> toProductResponseDtoList(List<Product> productList);
}