package guru.sfg.springframework.msscbeerservice.service.inventory;

import guru.sfg.springframework.msscbeerservice.service.inventory.model.BeerInventoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Profile(value = "local-discovery")
@Service
@Slf4j
@RequiredArgsConstructor
public class BeerInventoryServiceFeignImpl implements BeerInventoryService {

    private final InventoryServiceFeignClient inventoryServiceFeignClient;

    @Override
    public Integer getOnHandFromInventory(UUID beerId) {
        log.debug("Calling inventory service / BeerId : " + beerId);

        ResponseEntity<List<BeerInventoryDto>> listBeerInventoryDto =
                inventoryServiceFeignClient.getOnHandInventory(beerId);

        Integer onHand = Objects.requireNonNull(listBeerInventoryDto
                .getBody())
                .stream()
                .mapToInt(BeerInventoryDto::getQuantityOnHand)
                .sum();

        log.debug("BeerId : " + beerId + " On hand is : " + onHand);

        return onHand;

    }
}
