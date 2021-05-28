package com.inthebytes.searchservice.controller;

import com.inthebytes.searchservice.entity.Food;
import com.inthebytes.searchservice.entity.Restaurant;
import com.inthebytes.searchservice.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.Optional;

@RestController
@CrossOrigin(value = "http://localhost:3000")
public class ItemController {

	@Autowired
	SearchService service;

	@RequestMapping(path = "/foods", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> foodItem(@RequestParam(value = "foodId") String query) {
		Optional<Food> result;
		ResponseEntity<?> response;

		try {
			Long foodId = Long.parseLong(query);

			result = service.foodRequest(foodId);
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>("Food ID must be a number", HttpStatus.BAD_REQUEST);
		} catch (SQLException e) {
			response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}

		return response;
	}

	@RequestMapping(path = "/restaurants", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> restaurantItem(@RequestParam(value = "restaurantId") String query) {
		Restaurant result;
		ResponseEntity<?> response;

		try {
			Long restaurantId = Long.parseLong(query);

			result = service.restaurantRequest(restaurantId);
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>("Restaurant ID must be a number", HttpStatus.BAD_REQUEST);
		} catch (SQLException e) {
			response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}

		return response;
	}
}
