package com.inthebytes.searchservice.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inthebytes.searchservice.dto.RestaurantDTO;
import com.inthebytes.searchservice.service.RestaurantFilterService;

@RestController
@RequestMapping("/filter/restaurants")
@Tag(name = "search", description = "The search API")
public class RestaurantFilterController {
	
	@Autowired
	RestaurantFilterService service;

	@Operation(summary = "Get list of restaurants by filter params", description = "", tags = { "search" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantDTO.class, type = "List")),
					@Content(mediaType = "application/xml", schema = @Schema(implementation = RestaurantDTO.class, type = "List"))
			}),
			@ApiResponse(responseCode = "204", description = "Request has invalid parameters", content = @Content)
	})
	@GetMapping(value="")
	public ResponseEntity<List<RestaurantDTO>> filter(
			@RequestParam(value = "zip-code", required = false, defaultValue = "0") Integer zip,
			@RequestParam(value = "city", required = false, defaultValue = "_") String city,
			@RequestParam(value = "cuisine", required = false, defaultValue = "_") String cuisine,
			@RequestParam(value = "price-max", required = false, defaultValue = "0.0") Double priceMax,
			@RequestParam(value = "price-min", required = false, defaultValue = "0.0") Double priceMin) {
		
		List<RestaurantDTO> results = service.applyFilters(zip, city, cuisine, priceMax, priceMin);
		if (results == null) 
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		else
			return ResponseEntity.ok().body(results);
	}

	@Operation(summary = "Filter a list of provided restaurants by filter params", description = "", tags = { "search" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "successful operation", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantDTO.class, type = "List")),
					@Content(mediaType = "application/xml", schema = @Schema(implementation = RestaurantDTO.class, type = "List"))
			}),
			@ApiResponse(responseCode = "204", description = "Request has invalid parameters", content = @Content)
	})
	@PostMapping(value="")
	public ResponseEntity<List<RestaurantDTO>> filterResults(@Valid @RequestBody List<RestaurantDTO> results,
			@RequestParam(value = "zip-code", required = false, defaultValue = "0") Integer zip,
			@RequestParam(value = "city", required = false, defaultValue = "_") String city,
			@RequestParam(value = "cuisine", required = false, defaultValue = "_") String cuisine,
			@RequestParam(value = "price-max", required = false, defaultValue = "0.0") Double priceMax,
			@RequestParam(value = "price-min", required = false, defaultValue = "0.0") Double priceMin) {
		
		List<RestaurantDTO> filtered = service.layerFilters(results, zip, city, cuisine, priceMax, priceMin);
		if (filtered == null || filtered.size() == 0)
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		else
			return ResponseEntity.ok().body(filtered);
	}
}
