package guru.sfg.beer.order.service.services.order;

import guru.sfg.beer.order.service.config.JmsConfig;
import guru.sfg.beer.order.service.repositories.BeerOrderRepository;
import guru.sfg.beer.order.service.services.BeerOrderManager;
import guru.sfg.brewery.model.BeerOrderDto;
import guru.sfg.brewery.model.events.AllocateOrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class AllocateOrderListener {

    private final JmsTemplate jmsTemplate;
    private final BeerOrderManager beerOrderManager;

    @JmsListener(destination = JmsConfig.ALLOCATION_ORDER_RESPONSE_QUEUE)
    public void listen(AllocateOrderResponse allocateOrderResponse){
        if(allocateOrderResponse.getBeerOrderDto() == null){
            log.debug("NE DOBIJAMO NAZAD DTO OD INVENTORY SERVICA");
        }else{
            log.debug("DOBIJAMO DTO NORMALNO");
        }
        if(!allocateOrderResponse.getAllocationError() && !allocateOrderResponse.getPendingInventory()){
            //allocated normally
            beerOrderManager.beerOrderAllocationPassed(allocateOrderResponse.getBeerOrderDto());
        } else if(!allocateOrderResponse.getAllocationError() && allocateOrderResponse.getPendingInventory()) {
            //pending inventory
            beerOrderManager.beerOrderAllocationPendingInventory(allocateOrderResponse.getBeerOrderDto());
        } else if(allocateOrderResponse.getAllocationError()){
            //allocation error
            beerOrderManager.beerOrderAllocationFailed(allocateOrderResponse.getBeerOrderDto());
        }

    }
}
