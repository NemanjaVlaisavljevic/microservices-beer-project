package guru.sfg.springframework.msscbeerservice.service;

import guru.sfg.springframework.msscbeerservice.domain.Beer;
import guru.sfg.springframework.msscbeerservice.repository.BeerRepository;
import guru.sfg.springframework.msscbeerservice.web.mapper.BeerMapper;
import guru.sfg.brewery.model.BeerDto;
import guru.sfg.brewery.model.BeerPagedList;
import guru.sfg.brewery.model.BeerStyleEnum;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;


    public BeerServiceImpl(BeerRepository beerRepository, BeerMapper beerMapper) {
        this.beerRepository = beerRepository;
        this.beerMapper = beerMapper;
    }

    @Cacheable(cacheNames = "beerCache" , key = "#beerId" , condition = "#showInventoryOnHand == false ")
    @Override
    public BeerDto getById(UUID beerId, Boolean showInventoryOnHand) {
        if(showInventoryOnHand){
            return beerMapper.beerToBeerDtoWithInventory(beerRepository.findById(beerId).get());
        }else{
            return beerMapper.beerToBeerDto(beerRepository.findById(beerId).get());
        }
    }

    @Cacheable(cacheNames = "beerUpcCache")
    @Override
    public BeerDto getByUpc(String upc, Boolean showInventoryOnHand) {
       if(showInventoryOnHand){
            return beerMapper.beerToBeerDtoWithInventory(beerRepository.findByUpc(upc));

       }else {
            return beerMapper.beerToBeerDto(beerRepository.findByUpc(upc));

       }
    }

    @Override
    public BeerDto saveNewBeer(BeerDto beerDto) {
        Beer savingBeer = beerMapper.beerDtoToBeer(beerDto);
        beerRepository.save(savingBeer);
        return beerDto;
    }

    @Override
    public BeerDto updateBeer(UUID beerId, BeerDto beerDto) {
        Beer updatedBeer = beerRepository.findById(beerId).get();
        updatedBeer = beerMapper.beerDtoToBeer(beerDto);
        beerRepository.save(updatedBeer);
        return beerDto;
    }



    @Cacheable(cacheNames = "beerListCache" , condition = "#showInventoryOnHand == false")
    @Override
    public BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyleEnum, PageRequest pageRequest, Boolean showInventoryOnHand) {

        BeerPagedList beerPagedList;
        Page<Beer> beerPage;


        if(!StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyleEnum)){
            beerPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName , beerStyleEnum , pageRequest);
        }else if(!StringUtils.isEmpty(beerName) && StringUtils.isEmpty(beerStyleEnum)){
            beerPage = beerRepository.findAllByBeerName(beerName , pageRequest);
        }else if(StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyleEnum)){
            beerPage = beerRepository.findAllByBeerStyle(beerStyleEnum , pageRequest);
        }else{
            beerPage = beerRepository.findAll(pageRequest);
        }

        if(showInventoryOnHand){
            beerPagedList = new BeerPagedList(
                    beerPage.getContent()
                            .stream()
                            .map(beerMapper::beerToBeerDtoWithInventory)
                            .collect(Collectors.toList()),
                    PageRequest.of(beerPage.getPageable().getPageNumber() ,
                            beerPage.getPageable().getPageSize()),
                    beerPage.getTotalElements());
        }else {
            beerPagedList = new BeerPagedList(
                    beerPage.getContent()
                            .stream()
                            .map(beerMapper::beerToBeerDto)
                            .collect(Collectors.toList()),
                    PageRequest.of(beerPage.getPageable().getPageNumber() ,
                            beerPage.getPageable().getPageSize()),
                    beerPage.getTotalElements());
        }
        return beerPagedList;
    }

}
