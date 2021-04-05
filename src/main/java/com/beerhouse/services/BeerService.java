package com.beerhouse.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beerhouse.dto.BeerDTO;
import com.beerhouse.dto.BeerRequestDTO;
import com.beerhouse.exceptions.ObjectNotFoundException;
import com.beerhouse.models.Beer;
import com.beerhouse.repositories.BeerRepository;

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
    public Beer updateAllDataBeer(Long id, BeerRequestDTO beerRequestDTO) {
        Beer beer = findById(id);
        updateAllData(beer, beerRequestDTO);
        return beerRepository.save(beer);
    }
    
    @Transactional
    public Beer updateSomeDataBeer(Long id, BeerDTO beerDTO) {
        Beer beer = findById(id);
        updateSomeData(beer, beerDTO);
        return beerRepository.save(beer);
    }

    @Transactional
    public void deleteBeer(Long id) {
        Beer beer = findById(id);
        beerRepository.delete(beer);
    }

    private void updateAllData(Beer beer, BeerRequestDTO updateBeer) {
        beer.setName(updateBeer.getName());
        beer.setIngredients(updateBeer.getIngredients());
        beer.setAlcoholContent(updateBeer.getAlcoholContent());
        beer.setPrice(updateBeer.getPrice());
        beer.setCategory(updateBeer.getCategory());
    }
    
    private void updateSomeData(Beer beer, BeerDTO updateBeer) {
        beer.setName(ifNull(updateBeer.getName(), beer.getName()));
        beer.setIngredients(ifNull(updateBeer.getIngredients(), beer.getIngredients()));
        beer.setAlcoholContent(ifNull(updateBeer.getAlcoholContent(), beer.getAlcoholContent()));
        beer.setPrice(ifNull(updateBeer.getPrice(), beer.getPrice()));
        beer.setCategory(ifNull(updateBeer.getCategory(), beer.getCategory()));
    }
    
    public static String ifNull(String updateData, String defaultData) {
        return updateData == null ? defaultData : updateData;
    }
    
    public static Double ifNull(Double updateData, Double defaultData) {
        return updateData == null ? defaultData : updateData;
    }
}