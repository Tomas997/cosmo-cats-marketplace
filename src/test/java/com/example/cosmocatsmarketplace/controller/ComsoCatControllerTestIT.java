package com.example.cosmocatsmarketplace.controller;
import com.example.cosmocatsmarketplace.featuretoggle.FeatureToggleExtension;
import com.example.cosmocatsmarketplace.featuretoggle.FeatureToggles;
import com.example.cosmocatsmarketplace.featuretoggle.annotation.DisabledFeatureToggle;
import com.example.cosmocatsmarketplace.featuretoggle.annotation.EnabledFeatureToggle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("CosmoCat Controller IT")
@ExtendWith(FeatureToggleExtension.class)
class ComsoCatControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisabledFeatureToggle(FeatureToggles.COSMO_CATS)
    void shouldGet404FeatureDisabled() throws Exception {
        mockMvc.perform(get("/api/v1/cosmo-cats")).andExpect(status().isNotFound());
    }

    @Test
    @EnabledFeatureToggle(FeatureToggles.COSMO_CATS)
    void shouldGet200() throws Exception {
        mockMvc.perform(get("/api/v1/cosmo-cats")).andExpect(status().isOk());
    }
}