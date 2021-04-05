package com.beerhouse.resources;

import com.beerhouse.dto.BeerDTO;
import com.beerhouse.models.Beer;
import com.beerhouse.services.BeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/beers")
public class BeerResource {

    @Autowired
    private BeerService beerService;

    @GetMapping("/{id}")
    public ResponseEntity<Beer> findById(@PathVariable Long id) {
        return ResponseEntity.ok(beerService.findById(id));
    }

    @PostMapping()
    public ResponseEntity<Beer> saveBeer(@RequestBody BeerDTO beerDTO) {
        Beer beer = beerService.saveBeer(new Beer(beerDTO));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(beer.getId()).toUri();
        return ResponseEntity.created(uri).body(beer);
    }

    @GetMapping()
    public ResponseEntity<List<Beer>> findAll() {
        return ResponseEntity.ok(beerService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBeer(@PathVariable Long id, @RequestBody BeerDTO beerDTO) {
        beerService.updateBeer(id, beerDTO);
        return ResponseEntity.ok().build();
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateBeerPatch(@PathVariable Long id, @RequestBody BeerDTO beerDTO) {
        beerService.updateBeer(id, beerDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBeer(@PathVariable Long id) {
        beerService.deleteBeer(id);
        return ResponseEntity.noContent().build();
    }
}