package com.example.cosmocatsmarketplace.domain;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class CosmoCat {
    UUID id;
    String name;
    String email;
}