package com.beerhouse.resources;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.beerhouse.dto.BeerDTO;
import com.beerhouse.models.Beer;
import com.beerhouse.services.BeerService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class BeerResourceTest {

	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
	private BeerService beerService;
	
	@Test
	public void getBeerByIdWithSucess() throws Exception {
		Beer beer = new Beer(
				2L, 
				"Beer test",
				"ingredients test",
				"alcoholContent test",
				0.00,
				"category test");
		
		doReturn(beer).when(beerService).findById(2L);
		
		this.mockMvc.perform(get("/beers/2"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name", is("Beer test")));
	}
	
	@Test
	public void getAllBeerWithSucess() throws Exception {
		Beer beer1 = new Beer(
				2L, 
				"Beer test",
				"ingredients test",
				"alcoholContent test",
				0.00,
				"category test");
		
		Beer beer2 = new Beer(
				3L, 
				"Beer test",
				"ingredients test",
				"alcoholContent test",
				0.00,
				"category test");
		
		doReturn(Lists.newArrayList(beer1, beer2)).when(beerService).findAll();
		
		this.mockMvc.perform(get("/beers"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[1].id", is(3)));
	}
	
	@Test
	public void saveBeerWithSucess() throws Exception {
		BeerDTO beerDTO = new BeerDTO(
				"Beer test",
				"ingredients test",
				"alcoholContent test",
				0.00,
				"category test");
		Beer beer = new Beer(beerDTO);
		beer.setId(2L);
		
		doReturn(beer).when(beerService).saveBeer(any());
		
		this.mockMvc.perform(post("/beers")
				.content(asJsonString(beerDTO))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(header().string(HttpHeaders.LOCATION, "http://localhost/beers/2"))
			.andReturn();
	}
	
	@Test
	public void updateBeerWithSucess() throws Exception {
		Beer beer = new Beer(
				3L, 
				"Beer test",
				"ingredients test",
				"alcoholContent test",
				0.00,
				"category test");
		
		BeerDTO beerDTO = new BeerDTO(
				"Beer test update",
				"ingredients test update",
				"alcoholContent test update",
				0.01,
				"category test update");
		
		doReturn(beer).when(beerService).updateBeer(3L, beerDTO);
		
		this.mockMvc.perform(put("/beers/3")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(beerDTO)))
		.andExpect(status().isOk());
	}
	
	@Test
	public void updateBeerPatchWithSucess() throws Exception {
		Beer beer = new Beer(
				3L, 
				"Beer test",
				"ingredients test",
				"alcoholContent test",
				0.00,
				"category test");
		
		BeerDTO beerDTO = new BeerDTO(
				"Beer test update",
				"ingredients test update",
				"alcoholContent test update",
				0.01,
				"category test update");
		
		doReturn(beer).when(beerService).updateBeer(3L, beerDTO);
		
		this.mockMvc.perform(patch("/beers/3")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(beerDTO)))
		.andExpect(status().isOk());
	}
	
	@Test
	public void deleteBeerById() throws Exception {
		Beer beer = new Beer(
				2L, 
				"Beer test",
				"ingredients test",
				"alcoholContent test",
				0.00,
				"category test");

		doNothing().when(beerService).deleteBeer(beer.getId());
		
		this.mockMvc.perform(delete("/beers/2"))
			.andExpect(status().isNoContent());
	}
	
	static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}