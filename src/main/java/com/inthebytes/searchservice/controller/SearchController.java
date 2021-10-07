package com.inthebytes.searchservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.inthebytes.searchservice.service.SearchService;
import com.inthebytes.stacklunch.data.food.FoodDto;
import com.inthebytes.stacklunch.data.restaurant.RestaurantDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/search")
@Tag(name = "search", description = "The search API")
public class SearchController {

	@Autowired
	SearchService service;
	
	private final String sortingCriteria = "price";
	
	private Integer checkPageNumber(Integer pageNumber) {
		return (pageNumber < 0) ? 0 : pageNumber; 
	}
	
	private Boolean sortByPriceAscending(String sortOption) {
		return !sortOption.equals("high");
	}

	@Operation(summary = "Search for food with query", description = "", tags = { "search" })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = FoodDto.class, type = "Page List")),
			@Content(mediaType = "application/xml", schema = @Schema(implementation = FoodDto.class, type = "Page List")) }),
			@ApiResponse(responseCode = "400", description = "Request has invalid parameters", content = @Content) })
	@GetMapping(path = "/food")
	@ResponseBody
	public ResponseEntity<Page<FoodDto>> foodSearch(@RequestParam(value = "query") String query,
			@RequestParam(value = "sort", required = false, defaultValue = "low") String sortOption,
			@RequestParam(value = "page", required = false, defaultValue = "0") Integer pageNumber) {
		
		return ResponseEntity.ok(service.foodSearch(query, sortingCriteria, sortByPriceAscending(sortOption), checkPageNumber(pageNumber)));
	}

	@Operation(summary = "Search for food by ID", description = "", tags = { "search" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = FoodDto.class)),
					@Content(mediaType = "application/xml", schema = @Schema(implementation = FoodDto.class)) }),
			@ApiResponse(responseCode = "400", description = "Request has invalid parameters", content = @Content) })
	@GetMapping(path = "/food/{food-id}")
	@ResponseBody
	public ResponseEntity<FoodDto> foodId(@PathVariable(value = "food-id") String foodId) {

		return ResponseEntity.ok().body(service.foodById(foodId));

	}

	@Operation(summary = "Search for restaurant by query", description = "", tags = { "search" })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantDto.class, type = "Page List")),
			@Content(mediaType = "application/xml", schema = @Schema(implementation = RestaurantDto.class, type = "Page List")) }),
			@ApiResponse(responseCode = "400", description = "Request has invalid parameters", content = @Content) })
	@GetMapping(path = "/restaurant")
	@ResponseBody
	public ResponseEntity<Page<RestaurantDto>> restaurantSearch(@RequestParam(value = "query") String query,
			@RequestParam(value = "sort", required = false, defaultValue = "low") String sortOption,
			@RequestParam(value = "page", required = false, defaultValue = "0") Integer pageNumber) {

		return ResponseEntity.ok(service.restaurantSearch(query, "name", true, checkPageNumber(pageNumber)));
	}
}