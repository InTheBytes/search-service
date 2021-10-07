package com.inthebytes.searchservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.inthebytes.searchservice.dao.FoodDao;
import com.inthebytes.searchservice.dao.RestaurantDao;
import com.inthebytes.stacklunch.data.food.Food;
import com.inthebytes.stacklunch.data.food.FoodDto;
import com.inthebytes.stacklunch.data.restaurant.RestaurantDto;

@Service
public class SearchService {
	
	@Autowired
	FoodDao foodRepo;
	
	@Autowired
	RestaurantDao restaurantRepo;

	public Page<FoodDto> foodSearch(String query, String sort, Boolean ascending, Integer pageNumber) {
		return FoodDto.convert(
				foodRepo.findByNameContaining(query, PageRequest.of(pageNumber, 10, Sort.by((ascending)? Sort.Direction.ASC : Sort.Direction.DESC, sort)))
				);
	}

	public FoodDto foodById(String foodId) {
		Food food = foodRepo.findByFoodId(foodId);
		food.setRestaurant(restaurantRepo.findByFoods(food));
		return FoodDto.convert(food);
	}

	public Page<RestaurantDto> restaurantSearch(String query, String sort, Boolean ascending, Integer pageNumber) {
		return RestaurantDto.convert(
				restaurantRepo.findByNameContaining(query, PageRequest.of(pageNumber, 10, Sort.by((ascending)? Sort.Direction.ASC : Sort.Direction.DESC, sort)))
				);
	}
}