package com.beerhouse.models;

import com.beerhouse.dto.BeerDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
public class Beer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String ingredients;
    private String alcoholContent;
    private Double price;
    private String category;

    public Beer(BeerDTO beerDTO) {
        this.name = beerDTO.getName();
        this.ingredients = beerDTO.getIngredients();
        this.alcoholContent = beerDTO.getAlcoholContent();
        this.price = beerDTO.getPrice();
        this.category = beerDTO.getCategory();
    }
}