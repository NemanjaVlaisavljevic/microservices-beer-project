package guru.sfg.beer.order.service.services.testcomponents;

import guru.sfg.beer.order.service.config.JmsConfig;
import guru.sfg.brewery.model.events.AllocateOrderRequest;
import guru.sfg.brewery.model.events.AllocateOrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BeerOrderAllocationListener {

    private final JmsTemplate jmsTemplate;


    @JmsListener(destination = JmsConfig.ALLOCATE_ORDER_QUEUE)
    public void listen(Message message){

        AllocateOrderRequest allocateOrderRequest = (AllocateOrderRequest) message.getPayload();

        boolean allocationError = false;
        boolean pendingInventory = false;
        boolean sendResponse = true;

        if(allocateOrderRequest.getBeerOrderDto().getCustomerRef() != null &&
                allocateOrderRequest.getBeerOrderDto().getCustomerRef().equals("fail-allocation")){
            allocationError = true;
        }
        if(allocateOrderRequest.getBeerOrderDto().getCustomerRef() != null &&
                allocateOrderRequest.getBeerOrderDto().getCustomerRef().equals("fail-pending-inventory")){
            pendingInventory = true;
        }else if(allocateOrderRequest.getBeerOrderDto().getCustomerRef() != null &&
                allocateOrderRequest.getBeerOrderDto().getCustomerRef().equals("dont-allocate")) {
            sendResponse = false;
        }

        boolean finalPendingInventory = pendingInventory;

        allocateOrderRequest.getBeerOrderDto().getBeerOrderLines().forEach(beerOrderLineDto -> {
            if(finalPendingInventory){
                beerOrderLineDto.setQuantityAllocated(beerOrderLineDto.getOrderQuantity() - 1);
            }else{
                beerOrderLineDto.setQuantityAllocated(beerOrderLineDto.getOrderQuantity());
            }

        });

       if(sendResponse){
           jmsTemplate.convertAndSend(JmsConfig.ALLOCATION_ORDER_RESPONSE_QUEUE ,
                   AllocateOrderResponse.builder()
                           .allocationError(allocationError)
                           .pendingInventory(pendingInventory)
                           .beerOrderDto(allocateOrderRequest.getBeerOrderDto())
                           .build());
       }
    }
}
