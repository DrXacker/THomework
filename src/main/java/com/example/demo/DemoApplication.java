package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) throws JsonProcessingException {
		ApplicationContext context = SpringApplication.run(DemoApplication.class, args);
//		WeatherApiClient clientService = context.getBean(WeatherApiClient.class);
//		Weather q = clientService.getCurrentWeather("London");
//		System.out.println(q);
	}

}
