package com.beerhouse.services;

import com.beerhouse.dto.BeerDTO;
import com.beerhouse.exceptions.ObjectNotFoundException;
import com.beerhouse.models.Beer;
import com.beerhouse.repositories.BeerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BeerService {

    @Autowired
    private BeerRepository beerRepository;

    @Transactional
    public Beer saveBeer(Beer beer) {
        return beerRepository.save(beer);
    }

    @Transactional(readOnly = true)
    public Beer findById(Long id) {
        return beerRepository.findById(id).orElseThrow(()
                -> new ObjectNotFoundException("Beer not found! Id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Beer> findAll() {
        return beerRepository.findAll();
    }

    @Transactional
    public Beer updateBeer(Long id, BeerDTO beerDTO) {
        Beer beer = findById(id);
        updateData(beer, beerDTO);
        return beerRepository.save(beer);
    }

    @Transactional
    public void deleteBeer(Long id) {
        Beer beer = findById(id);
        beerRepository.delete(beer);
    }

    private void updateData(Beer beer, BeerDTO updateBeer) {
        beer.setName(updateBeer.getName());
        beer.setIngredients(updateBeer.getIngredients());
        beer.setAlcoholContent(updateBeer.getAlcoholContent());
        beer.setPrice(updateBeer.getPrice());
        beer.setCategory(updateBeer.getCategory());
    }
}