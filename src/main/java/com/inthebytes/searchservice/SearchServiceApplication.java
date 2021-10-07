package com.inthebytes.searchservice;

import javax.annotation.Generated;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.inthebytes.stacklunch.StackLunchApplication;

@SpringBootApplication
@EnableJpaRepositories
public class SearchServiceApplication {

	@Generated(value = { "" })
	public static void main(String[] args) {
		StackLunchApplication.run(SearchServiceApplication.class, args);
	}
}
