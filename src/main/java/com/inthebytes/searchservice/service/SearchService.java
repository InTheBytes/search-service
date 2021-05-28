package com.inthebytes.searchservice.service;

import java.sql.SQLException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.inthebytes.searchservice.dao.FoodDao;
import com.inthebytes.searchservice.dao.RestaurantDao;
import com.inthebytes.searchservice.entity.Food;
import com.inthebytes.searchservice.entity.Restaurant;
import com.inthebytes.searchservice.mapper.RestaurantMapper;

@Service
public class SearchService {
	
	@Autowired
	FoodDao foodRepo;
	
	@Autowired
	RestaurantDao restaurantRepo;
	
	@Autowired
	RestaurantMapper mapper;
	
	public Page<Food> foodSearch(String query, String sort, String[] filter, Boolean ascending, Integer pageNumber) throws SQLException {
		return foodRepo.findByNameContaining(query, PageRequest.of(pageNumber, 10, Sort.by((ascending)? Sort.Direction.ASC : Sort.Direction.DESC, sort)));
	}
	
	public Page<Restaurant> restaurantSearch(String query, String sort, String[] filter, Boolean ascending, Integer pageNumber) throws SQLException {
		return restaurantRepo.findByNameContaining(query, PageRequest.of(pageNumber, 10, Sort.by((ascending)? Sort.Direction.ASC : Sort.Direction.DESC, sort)));
	}

	public Optional<Food> foodRequest(Long foodId) throws SQLException {
		return foodRepo.findById(foodId);
	}

	public Restaurant restaurantRequest(Long restaurantId) throws SQLException {
		return restaurantRepo.findByRestaurantId(restaurantId);
	}
}