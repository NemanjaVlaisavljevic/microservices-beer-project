package guru.sfg.springframework.msscbeerservice.service.inventory;

import java.util.UUID;

public interface BeerInventoryService {

    Integer getOnHandFromInventory(UUID beerId);
}
