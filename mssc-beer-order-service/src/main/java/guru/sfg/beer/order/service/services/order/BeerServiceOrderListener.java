package guru.sfg.beer.order.service.services.order;

import guru.sfg.beer.order.service.config.JmsConfig;
import guru.sfg.beer.order.service.services.BeerOrderManager;
import guru.sfg.brewery.model.events.ValidateOrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BeerServiceOrderListener {

    private final BeerOrderManager beerOrderManager;

    @JmsListener(destination = JmsConfig.VALIDATE_ORDER_RESPONSE)
    public void listen(ValidateOrderResponse validateOrderResponse){
        Boolean isValid = validateOrderResponse.getIsValid();
        UUID orderId = validateOrderResponse.getOrderId();

        beerOrderManager.validateOrder(orderId,isValid);
    }
}
