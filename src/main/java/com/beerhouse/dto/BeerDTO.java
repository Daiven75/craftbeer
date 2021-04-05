package com.beerhouse.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class BeerDTO {

    private String name;
    private String ingredients;
    private String alcoholContent;
    private Double price;
    private String category;
}