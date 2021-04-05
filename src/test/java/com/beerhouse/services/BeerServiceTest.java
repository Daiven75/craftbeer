package com.beerhouse.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.beerhouse.dto.BeerDTO;
import com.beerhouse.exceptions.ObjectNotFoundException;
import com.beerhouse.models.Beer;
import com.beerhouse.repositories.BeerRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class BeerServiceTest {

	@Mock
	private BeerRepository beerRepository;
	
	@InjectMocks
	private BeerService beerService;
	
	@Test
	public void saveBeerWithSucess() throws Exception {
		Beer beer = new Beer(
				3L, 
				"Beer test",
				"ingredients test",
				"alcoholContent test",
				0.00,
				"category test");
		
		when(beerRepository.findById(beer.getId())).thenReturn(Optional.empty());
		Assertions.assertThatCode(() -> beerService.saveBeer(beer)).doesNotThrowAnyException();
	}
	

	@Test
	public void findByIdWithSucess() throws Exception {
		BeerDTO beerDTO = new BeerDTO(
				"Beer test update",
				"ingredients test update",
				"alcoholContent test update",
				0.01,
				"category test update");
		Beer beer = new Beer(beerDTO);
		
		when(beerRepository.findById(1L)).thenReturn(Optional.of(beer));
		final Beer beerExpected = beerService.findById(1L);
		assertThat(beerExpected).isNotNull();
	}
	
	@Test
	public void findByIdThrowException() throws Exception {
		Long id = 22L;
		Optional<ObjectNotFoundException> exception = Optional.of(
				new ObjectNotFoundException("Beer not found! Id: " + id));
		when(beerRepository.findById(id)).thenThrow(exception.get());
		Assertions.assertThatCode(() -> beerService.findById(id)).isEqualTo(exception.get());
	}
	
	@Test
	public void findAllBeersWithSucess() throws Exception {
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
		
		List<Beer> listBeer = Lists.newArrayList(beer1, beer2);
		when(beerRepository.findAll()).thenReturn(listBeer);
		assertThat(listBeer).isNotEmpty();
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
		
		when(beerRepository.findById(beer.getId())).thenReturn(Optional.of(beer));
		Assertions.assertThatCode(() -> beerService.updateBeer(beer.getId(), beerDTO)).doesNotThrowAnyException();
	}
	
	@Test
	public void deleteBeerWithSucess() throws Exception {
		Beer beer = new Beer(
				3L, 
				"Beer test",
				"ingredients test",
				"alcoholContent test",
				0.00,
				"category test");
		
		when(beerRepository.findById(beer.getId())).thenReturn(Optional.of(beer));
		Assertions.assertThatCode(() -> beerService.deleteBeer(beer.getId())).doesNotThrowAnyException();
	}
}