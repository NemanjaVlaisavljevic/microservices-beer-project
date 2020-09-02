package guru.sfg.springframework.msscbeerservice.service.order;

import guru.sfg.brewery.model.events.BeerOrderDto;
import guru.sfg.brewery.model.events.BeerOrderLineDto;
import guru.sfg.brewery.model.events.ValidateOrderRequest;
import guru.sfg.brewery.model.events.ValidateOrderResponse;
import guru.sfg.springframework.msscbeerservice.config.JmsConfig;
import guru.sfg.springframework.msscbeerservice.repository.BeerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderValidationListener {

    private final JmsTemplate jmsTemplate;
    private final BeerRepository beerRepository;

    @Transactional
    @JmsListener(destination = JmsConfig.VALIDATE_ORDER)
    public void validateOrder(ValidateOrderRequest validateOrderRequest){

        BeerOrderDto beerOrderDto = validateOrderRequest.getBeerOrderDto();

        List<BeerOrderLineDto> beerOrderLineList = beerOrderDto.getBeerOrderLines();

        boolean isValid = false;

        for(BeerOrderLineDto beerOrderLineDto : beerOrderLineList){
            if(beerRepository.findByUpc(beerOrderLineDto.getUpc()) == null){
                isValid = false;
            }else{
                isValid = true;
            }
        }

        ValidateOrderResponse validateOrderResponse = ValidateOrderResponse.builder()
                .orderId(beerOrderDto.getId())
                .isValid(isValid)
                .build();

        log.debug("Order is valid with id : " + validateOrderRequest.getBeerOrderDto().getId());

        jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_RESPONSE , validateOrderResponse);

    }
}
