package guru.sfg.springframework.msscbeerservice.service.brewing;

import guru.sfg.brewery.model.BeerDto;
import guru.sfg.brewery.model.events.BrewBeerEvent;
import guru.sfg.brewery.model.events.NewInventoryEvent;
import guru.sfg.springframework.msscbeerservice.config.JmsConfig;
import guru.sfg.springframework.msscbeerservice.domain.Beer;
import guru.sfg.springframework.msscbeerservice.repository.BeerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BrewBeerListener {

    private final BeerRepository beerRepository;
    private final JmsTemplate jmsTemplate;

    @Transactional
    @JmsListener(destination = JmsConfig.BREWING_REQUEST_QUEUE)
    public void listen(BrewBeerEvent event){

        if(event.getBeerDto() == null){
            log.debug("NE PRIMAMO BEER DTO OD BREWING REQUESTA");
        }

        BeerDto beerDto = event.getBeerDto();


        Optional<Beer> beer = beerRepository.findById(beerDto.getId());

        beer.ifPresentOrElse(beer1 -> {
            beerDto.setQuantityOnHand(beer1.getQuantityToBrew());
        } , () ->
                log.debug("Couldnt find beer with order : " + beerDto.getId()));

        NewInventoryEvent newInventoryEvent = new NewInventoryEvent(beerDto);

        jmsTemplate.convertAndSend(JmsConfig.NEW_INVENTORY_QUEUE, newInventoryEvent);
    }
}
