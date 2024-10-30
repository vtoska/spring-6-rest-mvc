package vali.springframework.spring6restmvc.mapper;


import org.mapstruct.Mapper;
import vali.springframework.spring6restmvc.entities.Beer;
import vali.springframework.spring6restmvc.model.BeerDto;

@Mapper
public interface BeerMapper {

    Beer beerDtoToBeer(BeerDto dto);
    BeerDto beerToBeerDto(Beer beer);

}
