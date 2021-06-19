package com.inthebytes.searchservice.controller;

import java.sql.SQLException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.inthebytes.searchservice.dto.FoodDTO;
import com.inthebytes.searchservice.dto.RestaurantDTO;
import com.inthebytes.searchservice.service.SearchService;

@RestController
@RequestMapping("/search")
@Tag(name = "search", description = "The search API")
public class SearchController {

	@Autowired
	SearchService service;

	@Operation(summary = "Search for food with query", description = "", tags = { "search" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = FoodDTO.class, type = "Page List")),
					@Content(mediaType = "application/xml", schema = @Schema(implementation = FoodDTO.class, type = "Page List"))
			}),
			@ApiResponse(responseCode = "400", description = "Request has invalid parameters", content = @Content)
	})
	@GetMapping(path = "/food")
	@ResponseBody
	public ResponseEntity<?> foodSearch(
			@RequestParam(value = "query") String query, 
			@RequestParam(value = "sort", required = false, defaultValue = "low") String sortOption, 
			@RequestParam(value = "filter", required = false, defaultValue = "") String[] filters, 
			@RequestParam(value = "page", required = false, defaultValue = "0") String pageNumber) {
		
		Page<FoodDTO> result;
		ResponseEntity<?> response;

		try {
			Integer page = Integer.parseInt(pageNumber) - 1;
			if (page < 0) {
				page = 0;
			}

			Boolean direction;
			String sort = "";

			switch (sortOption) {
				case "high":
					sort = "price";
					direction = false;
					break;
				case "low":
				default:
					sort = "price";
					direction = true;
			}

			result = service.foodSearch(query, sort, filters, direction, page);
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>("Page must be a number", HttpStatus.BAD_REQUEST);
		} catch (SQLException e) {
			response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			e.printStackTrace();
		}

		return response;
	}

	@Operation(summary = "Search for food by ID", description = "", tags = { "search" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = FoodDTO.class)),
					@Content(mediaType = "application/xml", schema = @Schema(implementation = FoodDTO.class))
			}),
			@ApiResponse(responseCode = "400", description = "Request has invalid parameters", content = @Content)
	})
	@GetMapping(path = "/food/{food-id}")
	@ResponseBody
	public ResponseEntity<?> foodId(@PathVariable(value = "food-id") String foodId) {

		FoodDTO result;
		ResponseEntity<?> response;

		try {
			result = service.foodById(foodId);
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>("Page must be a number", HttpStatus.BAD_REQUEST);
		} catch (SQLException e) {
			response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			e.printStackTrace();
		}

		return response;
	}

	@Operation(summary = "Search for restaurant by query", description = "", tags = { "search" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantDTO.class, type = "Page List")),
					@Content(mediaType = "application/xml", schema = @Schema(implementation = RestaurantDTO.class, type = "Page List"))
			}),
			@ApiResponse(responseCode = "400", description = "Request has invalid parameters", content = @Content)
	})
	@GetMapping(path = "/restaurant")
	@ResponseBody
	public ResponseEntity<?> restaurantSearch(
			@RequestParam(value = "query") String query, 
			@RequestParam(value = "sort", required = false, defaultValue = "low") String sortOption, 
			@RequestParam(value = "filter", required = false, defaultValue = "") String[] filters, 
			@RequestParam(value = "page", required = false, defaultValue = "0") String pageNumber) {
		
		Page<RestaurantDTO> result;
		ResponseEntity<?> response;

		try {
			Integer page = Integer.parseInt(pageNumber) - 1;
			if (page < 0) {
				page = 0;
			}

			result = service.restaurantSearch(query, "name", filters, true, page);
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} catch (NumberFormatException e) {
			return new ResponseEntity<>("Page must be a number", HttpStatus.BAD_REQUEST);
		} catch (SQLException e) {
			response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			e.printStackTrace();
		}

		return response;
	}
}