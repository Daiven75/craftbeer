package com.beerhouse.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.beerhouse.dto.BeerDTO;
import com.beerhouse.dto.BeerRequestDTO;
import com.beerhouse.models.Beer;
import com.beerhouse.services.BeerService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/beers")
public class BeerResource {

    @Autowired
    private BeerService beerService;

    @ApiOperation(value = "Find Beer By id")
    @ApiResponses(value = {
    		@ApiResponse(code = 404, message = "Beer inexistent") })
    @GetMapping("/{id}")
    public ResponseEntity<Beer> findById(@PathVariable Long id) {
        return ResponseEntity.ok(beerService.findById(id));
    }

    @ApiOperation(value = "Save new Beer")
    @PostMapping()
    public ResponseEntity<Beer> saveBeer(@RequestBody BeerRequestDTO beerRequestDTO) {
        Beer beer = beerService.saveBeer(new Beer(beerRequestDTO));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(beer.getId()).toUri();
        return ResponseEntity.created(uri).body(beer);
    }

    @ApiOperation(value = "Find all Beers")
    @GetMapping()
    public ResponseEntity<List<Beer>> findAll() {
        return ResponseEntity.ok(beerService.findAll());
    }

    @ApiOperation(value = "Update totally a Beer")
    @ApiResponses(value = {
    		@ApiResponse(code = 404, message = "Beer inexistent") })
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAllDataBeer(@PathVariable Long id, 
    		@RequestBody @Valid BeerRequestDTO beerRequestDTO) {
        beerService.updateAllDataBeer(id, beerRequestDTO);
        return ResponseEntity.ok().build();
    }
    
    @ApiOperation(value = "Update partially a Beer")
    @ApiResponses(value = {
    		@ApiResponse(code = 404, message = "Beer inexistent") })
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateSomeDataBeer(@PathVariable Long id, @RequestBody BeerDTO beerDTO) {
        beerService.updateSomeDataBeer(id, beerDTO);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Delete Beer")
    @ApiResponses(value = {
    		@ApiResponse(code = 404, message = "Beer inexistent") })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBeer(@PathVariable Long id) {
        beerService.deleteBeer(id);
        return ResponseEntity.noContent().build();
    }
}