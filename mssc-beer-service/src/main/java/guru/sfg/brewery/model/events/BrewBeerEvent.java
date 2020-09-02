package guru.sfg.brewery.model.events;

import guru.sfg.brewery.model.BeerDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class BrewBeerEvent extends BeerEvent {

    public BrewBeerEvent(BeerDto beerDto) {
            super(beerDto);
    }

}
