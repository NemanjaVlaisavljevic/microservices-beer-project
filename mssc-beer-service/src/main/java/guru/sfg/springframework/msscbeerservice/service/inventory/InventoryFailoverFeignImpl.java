package guru.sfg.springframework.msscbeerservice.service.inventory;

import guru.sfg.springframework.msscbeerservice.service.inventory.model.BeerInventoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class InventoryFailoverFeignImpl implements InventoryServiceFeignClient{

    private final InventoryFailoverFeign inventoryFailoverFeign;

    @Override
    public ResponseEntity<List<BeerInventoryDto>> getOnHandInventory(UUID beerId) {
       return inventoryFailoverFeign.getOnHandInventory();
    }
}
