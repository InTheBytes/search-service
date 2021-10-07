package com.inthebytes.searchservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.inthebytes.searchservice.dao.FoodDao;
import com.inthebytes.searchservice.dao.RestaurantDao;
import com.inthebytes.stacklunch.data.food.Food;
import com.inthebytes.stacklunch.data.food.FoodDto;
import com.inthebytes.stacklunch.data.restaurant.Restaurant;
import com.inthebytes.stacklunch.data.restaurant.RestaurantDto;

@ExtendWith(MockitoExtension.class)
public class SearchServiceTest {

	@Mock
	FoodDao foodRepo;
	
	@Mock
	RestaurantDao restaurantRepo;
	
	@Autowired
	@InjectMocks
	SearchService service;
	
	@Test
	public void foodSearchTest() throws SQLException {
		Page<Food> page = Page.empty();
		Page<FoodDto> result = Page.empty();
		when(foodRepo.findByNameContaining("", PageRequest.of(1, 10, Sort.by(Sort.Direction.ASC, "low")))).thenReturn(page);
		
		assertThat(service.foodSearch("", "low", true, 1)).isEqualTo(result);
	}
	
	@Test
	public void restaurantSearchTest() throws SQLException {
		Page<Restaurant> page = Page.empty();
		Page<RestaurantDto> result = Page.empty();
		when(restaurantRepo.findByNameContaining("", PageRequest.of(1, 10, Sort.by(Sort.Direction.ASC, "low")))).thenReturn(page);
		
		assertThat(service.restaurantSearch("", "low", true, 1)).isEqualTo(result);
	}
}
