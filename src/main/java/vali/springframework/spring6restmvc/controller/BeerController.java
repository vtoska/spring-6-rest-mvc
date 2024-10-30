package vali.springframework.spring6restmvc.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vali.springframework.spring6restmvc.model.Beer;
import vali.springframework.spring6restmvc.services.BeerService;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Slf4j
@RequiredArgsConstructor
@RestController
public class BeerController {

    public static final String BEER_URI = "/api/v1/beer";
    public static final String Beer_BY_ID_URI = BEER_URI +"/{beerId}";

    private final BeerService beerService;

    @PatchMapping(Beer_BY_ID_URI)
    public ResponseEntity updateBeerPatchById(@PathVariable("beerId")UUID beerId, @RequestBody Beer beer) {
        beerService.PatchBeerById(beerId,beer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(Beer_BY_ID_URI)
    public ResponseEntity deleteById(@PathVariable("beerId")UUID beerId) {
        beerService.deleteBeerById(beerId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(Beer_BY_ID_URI)
    public ResponseEntity updateById(@PathVariable("beerId")UUID beerId, @RequestBody Beer beer) {
        beerService.updateBeerById(beerId,beer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

   @PostMapping(BEER_URI)
   public ResponseEntity<Void> createNewBeer(@RequestBody Beer beer) {
       Beer savedBeer = beerService.saveNewBeer(beer);
       URI location = URI.create(BEER_URI + savedBeer.getId());
       return ResponseEntity.created(location).build();
   }


    @GetMapping(value = BEER_URI)
    public ResponseEntity<List<Beer>> listBeers() {
        List<Beer> beers = beerService.listBeers();
        return ResponseEntity.ok(beers);
    }

    @GetMapping(value = Beer_BY_ID_URI)
    public Beer getBeerById(@PathVariable("beerId") UUID beerId) {

        log.debug("Get beer by id  - in controller ");

        return beerService.getBeerById(beerId).orElseThrow(NotFoundException::new);
    }

}
