package com.inthebytes.searchservice;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.inthebytes.stacklunch.StackLunchDataSource;
import com.inthebytes.stacklunch.UniversalStackLunchConfiguration;

@Configuration
@Import({UniversalStackLunchConfiguration.class, StackLunchDataSource.class, SearchServiceSecurity.class})
public class SearchServiceTestConfig {

}
