package com.eis.czc.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfiguration {

	@Bean
	public RestTemplate getRestTemplate(){
		RestTemplate restTemplate= new RestTemplate();
		return restTemplate;
	}

	@Bean
	public HttpHeaders getMultiValueMap(){
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type","application/json;charset=UTF-8");
		return headers;
	}
}
