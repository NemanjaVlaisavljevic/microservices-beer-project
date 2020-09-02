package guru.sfg.springframework.msscbeerservice.web.controller;

import guru.sfg.springframework.msscbeerservice.service.BeerService;
import guru.sfg.brewery.model.BeerDto;
import guru.sfg.brewery.model.BeerPagedList;
import guru.sfg.brewery.model.BeerStyleEnum;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/beer")
public class BeerController {

    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 25;

    private final BeerService beerService;

    public BeerController(BeerService beerService) {
        this.beerService = beerService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BeerPagedList> beerList(
            @RequestParam(value = "pageNumber" , required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "beerName" , required = false) String beerName,
            @RequestParam(value = "beerStyle", required = false) BeerStyleEnum beerStyleEnum,
            @RequestParam(value = "showInventoryOnHand" , required = false) Boolean showInventoryOnHand){

        if(showInventoryOnHand == null){
            showInventoryOnHand = false;
        }

        if(pageNumber == null || pageNumber < 0){
            pageNumber = DEFAULT_PAGE_NUMBER;
        }
        if(pageSize == null || pageSize < 1 ){
            pageSize = DEFAULT_PAGE_SIZE;
        }

        BeerPagedList beerList = beerService.listBeers(beerName , beerStyleEnum , PageRequest.of(pageNumber , pageSize) , showInventoryOnHand);

        return new ResponseEntity<>(beerList , HttpStatus.OK);
    }

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDto> getBeerById(@PathVariable UUID beerId,
                                               @RequestParam(value = "showInventoryOnHand" , required = false)
                                               Boolean showInventoryOnHand){
        if(showInventoryOnHand == null){
            showInventoryOnHand = false;
        }

        return new ResponseEntity<>(beerService.getById(beerId , showInventoryOnHand) , HttpStatus.OK);
    }

    @GetMapping("/beerUpc/{upc}")
    public ResponseEntity<BeerDto> getBeerByUpc(@PathVariable String upc ,
                                                @RequestParam(value = "showInventoryOnHand" , required = false)
                                                Boolean showInventoryOnHand){
        if(showInventoryOnHand == null){
            showInventoryOnHand = false;
        }
            return new ResponseEntity<>(beerService.getByUpc(upc , showInventoryOnHand) , HttpStatus.OK);

    }

    @PostMapping()
    public ResponseEntity saveNewBeer(@Valid @RequestBody BeerDto beer){

        return new ResponseEntity(beerService.saveNewBeer(beer) , HttpStatus.CREATED);
    }

    @PutMapping("/{beerId}")
    public ResponseEntity updateBeerById(@PathVariable UUID beerId ,@Valid @RequestBody BeerDto beerDto){

        return new ResponseEntity(beerService.updateBeer(beerId , beerDto) , HttpStatus.NO_CONTENT);
    }

}
