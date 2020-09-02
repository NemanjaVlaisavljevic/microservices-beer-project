package com.sfg.inventoryfailover.controller;

import com.sfg.inventoryfailover.model.BeerInventoryDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
public class InventoryFailoverController {

    @GetMapping("/inventory-failover")
    public List<BeerInventoryDto> getInventoryDtoList(){
        BeerInventoryDto beerInventoryDto = BeerInventoryDto.builder()
                .id(UUID.randomUUID())
                .beerId(UUID.fromString("00000000-0000-0000-0000-000000000000"))
                .quantityOnHand(999)
                .createdDate(OffsetDateTime.now())
                .lastModifiedDate(OffsetDateTime.now())
                .upc("72527273070")
                .build();
        List<BeerInventoryDto> beerInventoryDtoList = new ArrayList<BeerInventoryDto>();
        beerInventoryDtoList.add(beerInventoryDto);

        return beerInventoryDtoList;
    }

}
