package guru.sfg.springframework.msscbeerservice.web.mapper;

import guru.sfg.springframework.msscbeerservice.domain.Beer;
import guru.sfg.springframework.msscbeerservice.service.inventory.BeerInventoryService;
import guru.sfg.brewery.model.BeerDto;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BeerMapperDecorator implements BeerMapper {

    private BeerInventoryService beerInventoryService;

    private BeerMapper mapper;

    @Autowired
    public void setBeerInventoryService(BeerInventoryService beerInventoryService){
        this.beerInventoryService = beerInventoryService;
    }

    @Autowired
    public void setMapper(BeerMapper mapper){
        this.mapper = mapper;
    }

    @Override
    public BeerDto beerToBeerDtoWithInventory(Beer beerObject) {
        BeerDto dto = mapper.beerToBeerDto(beerObject);
        dto.setQuantityOnHand(beerInventoryService.getOnHandFromInventory(beerObject.getId()));
        return dto;
    }

    @Override
    public BeerDto beerToBeerDto(Beer beerObject) {
        BeerDto dto = mapper.beerToBeerDto(beerObject);
        return dto;
    }

    @Override
    public Beer beerDtoToBeer(BeerDto beerDtoObject) {
       return mapper.beerDtoToBeer(beerDtoObject);
    }
}
