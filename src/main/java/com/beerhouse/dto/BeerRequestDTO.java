package com.beerhouse.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class BeerRequestDTO {

	@NotEmpty(message = "Mandatory filling")
	private String name;
	
	@NotEmpty(message = "Mandatory filling")
	private String ingredients;
	
	@NotEmpty(message = "Mandatory filling")
	private String alcoholContent;
	 
	@NotNull(message = "Mandatory filling")
	private Double price;
	
	@NotEmpty(message = "Mandatory filling")
	private String category;
}