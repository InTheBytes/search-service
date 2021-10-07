package com.inthebytes.searchservice.control;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.test.web.servlet.MockMvc;

import com.inthebytes.searchservice.SearchServiceTestConfig;
import com.inthebytes.searchservice.controller.SearchController;
import com.inthebytes.searchservice.service.SearchService;
import com.inthebytes.stacklunch.data.food.FoodDto;
import com.inthebytes.stacklunch.data.restaurant.RestaurantDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
		classes = {SearchServiceTestConfig.class, SearchController.class})
@AutoConfigureMockMvc
@EnableAutoConfiguration
public class SearchControllerTest {
	
	@MockBean
	SearchService service;
	
	@Autowired
	MockMvc mock;
	
	private Page<RestaurantDto> restaurantPage = Page.empty();
	private Page<FoodDto> foodPage = Page.empty();
	
	
	@Test
	public void foodSearchTest() throws Exception {
		when(service.foodSearch("s", "price", false, 1)).thenReturn(foodPage);
		
		mock.perform(get("/search/food")
		        .param("query","s")
		        .param("page","1")
		        .param("sort","high"))
		        .andExpect(status().isOk());
	}
	
	@Test
	public void foodNoParamsTest() throws Exception {
		when(service.foodSearch("s", "price", true, 0)).thenReturn(foodPage);
		
		mock.perform(get("/search/food")
		        .param("query","s"))
		        .andExpect(status().isOk());
	}
	
	@Test
	public void foodSearchBadParamsTest() throws Exception {
		mock.perform(get("/search/food")
		        .param("query","s")
		        .param("page", "hello"))
		        .andExpect(status().isBadRequest());
	}
	
	@Test
	public void restaurantSearchTest() throws Exception {
		when(service.restaurantSearch("s", "name", true, 1)).thenReturn(restaurantPage);
		
		mock.perform(get("/search/restaurant")
		        .param("query","s")
		        .param("page", "1"))
		        .andExpect(status().isOk());
	}

	@Test
	public void restaurantSearchNoParamsTest() throws Exception {
		when(service.restaurantSearch("s", "name", true, 0)).thenReturn(restaurantPage);
		
		mock.perform(get("/search/restaurant")
		        .param("query","s"))
		        .andExpect(status().isOk());
	}
	
	@Test
	public void restaurantSearchBadParamsTest() throws Exception {
		mock.perform(get("/search/restaurant")
		        .param("query","s")
		        .param("page", "hello"))
		        .andExpect(status().isBadRequest());
	}
}
