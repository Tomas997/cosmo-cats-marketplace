package com.example.cosmocatsmarketplace.service;

import com.example.cosmocatsmarketplace.dto.cosmocat.CosmoCatDto;
import com.example.cosmocatsmarketplace.service.impl.CosmoCatServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CosmoCatServiceImplTest {

    @Autowired
    private CosmoCatServiceImpl cosmoCatService;

    @Test
    void getCosmoCats_ShouldReturnListOfCosmoCats() {
        List<CosmoCatDto> cosmoCats = cosmoCatService.getCosmoCats();

        assertThat(cosmoCats).isNotNull()
                .hasSize(3);


        List<String> expectedNames = List.of("Lunar Whiskers", "Solar Paws", "Comet Claws");

        for (CosmoCatDto cosmoCat : cosmoCats) {
            assertThat(expectedNames).contains(cosmoCat.getName());
        }
    }

}
