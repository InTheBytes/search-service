package com.inthebytes.searchservice.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.inthebytes.stacklunch.data.food.Food;
import com.inthebytes.stacklunch.data.food.FoodRepository;

@Repository
public interface FoodDao extends FoodRepository {
	Page<Food> findByNameContaining(String query, Pageable pageable);
	Food findByFoodId(String foodId);
}
