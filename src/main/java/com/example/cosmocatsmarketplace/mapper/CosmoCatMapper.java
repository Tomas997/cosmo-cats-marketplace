package com.example.cosmocatsmarketplace.mapper;

import com.example.cosmocatsmarketplace.domain.CosmoCat;
import com.example.cosmocatsmarketplace.dto.cosmocat.CosmoCatDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CosmoCatMapper {

    CosmoCatDto toDto(CosmoCat cosmoCat);

    CosmoCat toDomain(CosmoCatDto cosmoCatDto);
}