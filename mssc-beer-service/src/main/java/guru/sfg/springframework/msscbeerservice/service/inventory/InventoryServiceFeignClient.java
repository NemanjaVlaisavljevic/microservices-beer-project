package guru.sfg.springframework.msscbeerservice.service.inventory;

import guru.sfg.springframework.msscbeerservice.config.FeignClientCommuncationConfig;
import guru.sfg.springframework.msscbeerservice.service.inventory.model.BeerInventoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "beer-inventory-service" , fallback = InventoryFailoverFeignImpl.class ,
                                                configuration = FeignClientCommuncationConfig.class)
public interface InventoryServiceFeignClient {

    @RequestMapping(method = RequestMethod.GET , value = BeerInventoryServiceRestTemplate.INVENTORY_PATH)
    ResponseEntity<List<BeerInventoryDto>> getOnHandInventory(@PathVariable UUID beerId);
}
