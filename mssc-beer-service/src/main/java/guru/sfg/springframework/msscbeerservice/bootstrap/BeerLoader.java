package guru.sfg.springframework.msscbeerservice.bootstrap;

import guru.sfg.springframework.msscbeerservice.domain.Beer;
import guru.sfg.springframework.msscbeerservice.repository.BeerRepository;
import guru.sfg.brewery.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class BeerLoader implements CommandLineRunner {

    public static final String BEER_1_UPC = "0631234200036";
    public static final String BEER_2_UPC = "0631234200037";
    public static final String BEER_3_UPC = "0631234200038";
    public static final String BEER_4_UPC = "0631234200039";
    public static final String BEER_5_UPC = "0631234200040";

    private final BeerRepository beerRepository;

    @Override
    public void run(String... args) throws Exception {
       if(beerRepository.count() == 0){
           loadBeerObjects();
       }
    }

    private void loadBeerObjects(){
        Beer b1 = Beer.builder()
                .beerName("Mango Bobs")
                .beerStyle(BeerStyleEnum.IPA.name())
                .minOnHand(12)
                .quantityToBrew(200)
                .price(new BigDecimal("12.95"))
                .upc(BEER_1_UPC)
                .build();
        Beer b2 = Beer.builder()
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyleEnum.PALE_ALE.name())
                .minOnHand(12)
                .quantityToBrew(200)
                .price(new BigDecimal("12.95"))
                .upc(BEER_2_UPC)
                .build();
        Beer b3 = Beer.builder()
                .beerName("Pinball Porter")
                .beerStyle(BeerStyleEnum.GOSE.name())
                .minOnHand(12)
                .quantityToBrew(200)
                .price(new BigDecimal("12.95"))
                .upc(BEER_3_UPC)
                .build();

        Beer b4 = Beer.builder()
                .beerName("Lav")
                .beerStyle(BeerStyleEnum.PILSNER.name())
                .minOnHand(15)
                .quantityToBrew(200)
                .price(new BigDecimal("11.95"))
                .upc(BEER_4_UPC)
                .build();
        Beer b5 = Beer.builder()
                .beerName("Jelen")
                .beerStyle(BeerStyleEnum.LAGER.name())
                .minOnHand(15)
                .quantityToBrew(200)
                .price(new BigDecimal("11.95"))
                .upc(BEER_5_UPC)
                .build();

        beerRepository.save(b1);
        beerRepository.save(b2);
        beerRepository.save(b3);
        beerRepository.save(b4);
        beerRepository.save(b5);
    }
}
