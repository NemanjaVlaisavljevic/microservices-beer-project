package guru.sfg.springframework.msscbeerservice.web.mapper;

import guru.sfg.springframework.msscbeerservice.domain.Beer;
import guru.sfg.brewery.model.BeerDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(uses=DateMapper.class)
@DecoratedWith(BeerMapperDecorator.class)
public interface BeerMapper {

    BeerDto beerToBeerDto(Beer beerObject);

    Beer beerDtoToBeer(BeerDto beerDtoObject);

    BeerDto beerToBeerDtoWithInventory(Beer beerObject);

}
