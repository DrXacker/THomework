package com.example.demo;

import com.example.demo.model.Weather;
import com.example.demo.service.client.WeatherApiClient;
import com.example.demo.transaction.jdbc.JdbcTransaction;
import com.example.demo.transaction.jpa.JpaTransaction;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner commandLineRunner(JdbcTransaction jdbcTransaction,
//											   JpaTransaction jpaTransaction,
//											   WeatherApiClient weatherApiClient) {
//		return args -> {
//			Weather dataFromApi = weatherApiClient.getCurrentWeather("Voronezh");
//			jpaTransaction.insertDataFromAPI(dataFromApi);
//
//			dataFromApi = weatherApiClient.getCurrentWeather("London");
//			jdbcTransaction.insertDataFromAPI(dataFromApi);
//		};
//	}
}
