package guru.sfg.brewery.model.events;

import guru.sfg.brewery.model.BeerOrderDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeallocateOrderRequest {

    private BeerOrderDto beerOrderDto;

    public BeerOrderDto getBeerOrderDto() {
        return this.beerOrderDto;
    }
}
