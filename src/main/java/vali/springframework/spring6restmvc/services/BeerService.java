package vali.springframework.spring6restmvc.services;

import org.springframework.stereotype.Service;
import vali.springframework.spring6restmvc.model.BeerDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface BeerService {

    List<BeerDto> listBeers();

    Optional<BeerDto> getBeerById(UUID id) ;

    BeerDto saveNewBeer(BeerDto beer);

    Optional<BeerDto> updateBeerById(UUID beerId, BeerDto beer);

    Boolean deleteBeerById(UUID beerId);

    Optional<BeerDto> PatchBeerById(UUID beerId, BeerDto beer);
}
