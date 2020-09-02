package guru.sfg.beer.inventory.service.services;

import guru.sfg.beer.inventory.service.config.JmsConfig;
import guru.sfg.brewery.model.BeerOrderDto;
import guru.sfg.brewery.model.events.AllocateOrderRequest;
import guru.sfg.brewery.model.events.AllocateOrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AllocationOrderListener {

    private final JmsTemplate jmsTemplate;
    private final AllocationService allocationService;


    @JmsListener(destination = JmsConfig.ALLOCATE_ORDER_QUEUE)
    public void listen(AllocateOrderRequest allocateOrderRequest) {
        BeerOrderDto beerOrderDto = allocateOrderRequest.getBeerOrderDto();

        Boolean pendingInventory = false;
        Boolean allocationError = false;


        try {
            Boolean allocationResult = allocationService.allocateOrder(beerOrderDto);

            if (allocationResult) {
                pendingInventory = false;
            } else {
                pendingInventory = true;
            }
        } catch (Exception e) {
            log.error("Allocation failed for Order id : " + allocateOrderRequest.getBeerOrderDto().getId());
            allocationError = true;
        }

        jmsTemplate.convertAndSend(JmsConfig.ALLOCATION_ORDER_RESPONSE_QUEUE,
                AllocateOrderResponse.builder()
                        .pendingInventory(pendingInventory)
                        .allocationError(allocationError)
                        .beerOrderDto(beerOrderDto)
                        .build());
    }

}
