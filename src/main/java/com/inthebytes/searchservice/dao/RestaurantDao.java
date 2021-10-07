package com.inthebytes.searchservice.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.inthebytes.stacklunch.data.food.Food;
import com.inthebytes.stacklunch.data.restaurant.Restaurant;
import com.inthebytes.stacklunch.data.restaurant.RestaurantRepository;

@Repository
public interface RestaurantDao extends RestaurantRepository {
	Restaurant findByRestaurantId(String id);
	Restaurant findByName(String name);
	Restaurant findByFoods(Food food);
	Page<Restaurant> findByNameContaining(String query, Pageable pageable);
}
