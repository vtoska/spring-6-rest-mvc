package vali.springframework.spring6restmvc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vali.springframework.spring6restmvc.model.BeerDto;
import vali.springframework.spring6restmvc.services.BeerService;

import java.net.URI;
import java.util.List;
import java.util.UUID;


@Slf4j
@RequiredArgsConstructor
@RestController
public class BeerController {

    public static final String BEER_URI = "/api/v1/beer";
    public static final String Beer_BY_ID_URI = BEER_URI +"/{beerId}";

    private final BeerService beerService;

    @PatchMapping(Beer_BY_ID_URI)
    public ResponseEntity updateBeerPatchById(@PathVariable("beerId")UUID beerId, @RequestBody BeerDto beer) {
        beerService.PatchBeerById(beerId,beer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(Beer_BY_ID_URI)
    public ResponseEntity deleteById(@PathVariable("beerId")UUID beerId) {
        beerService.deleteBeerById(beerId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(Beer_BY_ID_URI)
    public ResponseEntity updateById(@PathVariable("beerId")UUID beerId, @RequestBody BeerDto beer) {
        if(beerService.updateBeerById(beerId,beer).isEmpty()){
            throw new NotFoundException("Beer not found");
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

   @PostMapping(BEER_URI)
   public ResponseEntity handlePost (@RequestBody BeerDto beer) {
       BeerDto savedBeer = beerService.saveNewBeer(beer);
       HttpHeaders headers = new HttpHeaders();
       headers.add("Location",BEER_URI +"/" + savedBeer.getId().toString());

       return new  ResponseEntity (headers, HttpStatus.CREATED);
   }


    @GetMapping(value = BEER_URI)
    public List<BeerDto> listBeers() {
        return beerService.listBeers();
    }

    @GetMapping(value = Beer_BY_ID_URI)
    public BeerDto getBeerById(@PathVariable("beerId") UUID beerId) {

        log.debug("Get beer by id  - in controller ");

        return beerService.getBeerById(beerId).orElseThrow(NotFoundException::new);
    }

}
