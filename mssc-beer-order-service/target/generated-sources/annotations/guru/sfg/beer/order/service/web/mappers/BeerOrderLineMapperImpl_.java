package guru.sfg.beer.order.service.web.mappers;

import guru.sfg.beer.order.service.domain.BeerOrderLine;
import guru.sfg.beer.order.service.domain.BeerOrderLine.BeerOrderLineBuilder;
import guru.sfg.brewery.model.BeerOrderLineDto;
import guru.sfg.brewery.model.BeerOrderLineDto.BeerOrderLineDtoBuilder;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-08-30T18:48:03+0200",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 13.0.1 (Oracle Corporation)"
)
@Component
@Qualifier("delegate")
public class BeerOrderLineMapperImpl_ implements BeerOrderLineMapper {

    @Autowired
    private DateMapper dateMapper;

    @Override
    public BeerOrderLineDto beerOrderLineToDto(BeerOrderLine line) {

        BeerOrderLineDtoBuilder beerOrderLineDto = BeerOrderLineDto.builder();

        if ( line != null ) {
            beerOrderLineDto.id( line.getId() );
            if ( line.getVersion() != null ) {
                beerOrderLineDto.version( line.getVersion().intValue() );
            }
            beerOrderLineDto.createdDate( dateMapper.asOffsetDateTime( line.getCreatedDate() ) );
            beerOrderLineDto.lastModifiedDate( dateMapper.asOffsetDateTime( line.getLastModifiedDate() ) );
            beerOrderLineDto.upc( line.getUpc() );
            beerOrderLineDto.beerId( line.getBeerId() );
            beerOrderLineDto.orderQuantity( line.getOrderQuantity() );
            beerOrderLineDto.quantityAllocated( line.getQuantityAllocated() );
        }

        return beerOrderLineDto.build();
    }

    @Override
    public BeerOrderLine dtoToBeerOrderLine(BeerOrderLineDto dto) {

        BeerOrderLineBuilder beerOrderLine = BeerOrderLine.builder();

        if ( dto != null ) {
            beerOrderLine.id( dto.getId() );
            if ( dto.getVersion() != null ) {
                beerOrderLine.version( dto.getVersion().longValue() );
            }
            beerOrderLine.createdDate( dateMapper.asTimestamp( dto.getCreatedDate() ) );
            beerOrderLine.lastModifiedDate( dateMapper.asTimestamp( dto.getLastModifiedDate() ) );
            beerOrderLine.beerId( dto.getBeerId() );
            beerOrderLine.upc( dto.getUpc() );
            beerOrderLine.orderQuantity( dto.getOrderQuantity() );
            beerOrderLine.quantityAllocated( dto.getQuantityAllocated() );
        }

        return beerOrderLine.build();
    }
}
