package com.example.cosmocatsmarketplace.controller;

import com.example.cosmocatsmarketplace.dto.cosmocat.CosmoCatDto;
import com.example.cosmocatsmarketplace.featuretoggle.FeatureToggles;
import com.example.cosmocatsmarketplace.featuretoggle.FeatureToggle;
import com.example.cosmocatsmarketplace.service.CosmoCatService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/cosmo-cats")
public class CosmoCatController {

    private final CosmoCatService cosmoCatService;

    @GetMapping
    @FeatureToggle(FeatureToggles.COSMO_CATS)
    public ResponseEntity<List<CosmoCatDto>> getAllCosmoCats() {
        List<CosmoCatDto> cosmoCats = cosmoCatService.getCosmoCats();
        return ResponseEntity.ok(cosmoCats);
    }
}