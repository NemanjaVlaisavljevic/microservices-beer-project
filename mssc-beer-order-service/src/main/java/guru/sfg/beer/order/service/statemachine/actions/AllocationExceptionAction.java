package guru.sfg.beer.order.service.statemachine.actions;

import guru.sfg.beer.order.service.config.JmsConfig;
import guru.sfg.beer.order.service.domain.BeerOrderEventEnum;
import guru.sfg.beer.order.service.domain.BeerOrderStatusEnum;
import guru.sfg.beer.order.service.services.BeerOrderManagerImplementation;
import guru.sfg.brewery.model.events.AllocationFailEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class AllocationExceptionAction implements Action<BeerOrderStatusEnum , BeerOrderEventEnum> {

    private final JmsTemplate jmsTemplate;


    @Override
    public void execute(StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> stateContext) {
        String orderId =(String) stateContext.getMessage().getHeaders().get(BeerOrderManagerImplementation.ORDER_ID_HEADER);
        AllocationFailEvent allocationFailed = AllocationFailEvent.builder()
                .orderId(UUID.fromString(orderId))
                .build();
        jmsTemplate.convertAndSend(JmsConfig.ALLOCATION_FAILED_QUEUE , allocationFailed);

    }
}
