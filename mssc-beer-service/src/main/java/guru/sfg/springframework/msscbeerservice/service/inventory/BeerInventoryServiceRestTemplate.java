package guru.sfg.springframework.msscbeerservice.service.inventory;

import guru.sfg.springframework.msscbeerservice.service.inventory.model.BeerInventoryDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Profile("!local-discovery")
@Slf4j
@Component
public class BeerInventoryServiceRestTemplate implements BeerInventoryService {

    @Autowired
    private Environment environment;

    public static final String INVENTORY_PATH = "api/v1/beerInventory/{beerId}/inventory";
    private final RestTemplate restTemplate;
    private String beerInventoryServiceHost = environment.getProperty("beerInventoryServiceHost");
    private String inventoryUser = environment.getProperty("inventoryUser");
    private String inventoryPassword = environment.getProperty("inventoryPassword");

    public BeerInventoryServiceRestTemplate(RestTemplateBuilder restTemplate) {
        this.restTemplate = restTemplate
                .basicAuthentication(inventoryUser, inventoryPassword)
                .build();
    }

    @Override
    public Integer getOnHandFromInventory(UUID beerId) {


        ResponseEntity<List<BeerInventoryDto>> listResponseEntity = restTemplate.exchange(
                beerInventoryServiceHost + INVENTORY_PATH, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<BeerInventoryDto>>(){} , (Object) beerId);

        Integer onHand = listResponseEntity
                        .getBody()
                        .stream()
                        .mapToInt(BeerInventoryDto::getQuantityOnHand)
                        .sum();

        return onHand;
    }
}
