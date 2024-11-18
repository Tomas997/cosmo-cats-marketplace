package com.example.cosmocatsmarketplace.dto.cosmocat;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Value
@Builder
@Jacksonized
public class CosmoCatDto {

    UUID id;
    String name;
    String email;
}