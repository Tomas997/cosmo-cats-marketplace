package com.example.cosmocatsmarketplace.service.impl;

import com.example.cosmocatsmarketplace.dto.cosmocat.CosmoCatDto;
import com.example.cosmocatsmarketplace.service.CosmoCatService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CosmoCatServiceImpl implements CosmoCatService {

    @Override
    public List<CosmoCatDto> getCosmoCats() {
        return List.of(
                CosmoCatDto.builder()
                        .id(UUID.randomUUID())
                        .name("Lunar Whiskers")
                        .email("lunar.whiskers@cosmocats.com")
                        .build(),

                CosmoCatDto.builder()
                        .id(UUID.randomUUID())
                        .name("Solar Paws")
                        .email("solar.paws@cosmocats.com")
                        .build(),

                CosmoCatDto.builder()
                        .id(UUID.randomUUID())
                        .name("Comet Claws")
                        .email("comet.claws@cosmocats.com")
                        .build()
        );
    }
}