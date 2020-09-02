package guru.sfg.springframework.msscbeerservice.service.brewing;

import guru.sfg.brewery.model.events.BrewBeerEvent;
import guru.sfg.springframework.msscbeerservice.config.JmsConfig;
import guru.sfg.springframework.msscbeerservice.domain.Beer;
import guru.sfg.springframework.msscbeerservice.repository.BeerRepository;
import guru.sfg.springframework.msscbeerservice.service.inventory.BeerInventoryService;
import guru.sfg.springframework.msscbeerservice.web.mapper.BeerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrewingService {

    private final BeerRepository beerRepository;
    private final BeerInventoryService beerInventoryService;
    private final  JmsTemplate jmsTemplate;
    private final BeerMapper beerMapper;

    @Scheduled(fixedRate = 5000)
    public void checkForLowInventory(){
        List<Beer> beers = beerRepository.findAll();

        beers.forEach(beer -> {
            Integer invQOH = beerInventoryService.getOnHandFromInventory(beer.getId());
            log.debug("Min on hand is : " + beer.getMinOnHand());
            log.debug("Inventory is : " + invQOH);

            if(beer.getMinOnHand() >= invQOH){
                jmsTemplate.convertAndSend(JmsConfig.BREWING_REQUEST_QUEUE
                        , new BrewBeerEvent(beerMapper.beerToBeerDto(beer)));
            }
        });
    }
}
