package com.example.demo;

import com.example.demo.model.Weather;
import com.example.demo.model.db.City;
import com.example.demo.model.db.GuideWeather;
import com.example.demo.model.db.WeatherInCity;
import com.example.demo.service.client.WeatherApiClient;
import com.example.demo.service.jdbc.CityJdbcService;
import com.example.demo.service.jdbc.GuideWeatherJdbcService;
import com.example.demo.service.jdbc.WeatherInCityJdbcService;
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

	@Bean
	public CommandLineRunner commandLineRunner(JdbcTransaction jdbcTransaction,
											   JpaTransaction jpaTransaction,
											   CityJdbcService cityJdbcService,
											   GuideWeatherJdbcService guideWeatherJdbcService,
											   WeatherInCityJdbcService weatherInCityJdbcService,
											   WeatherApiClient weatherApiClient) {
		return args -> {
			Weather dataFromApi = weatherApiClient.getCurrentWeather("London");
			jdbcTransaction.insertDataFromAPI(dataFromApi);

			dataFromApi = weatherApiClient.getCurrentWeather("Paris");
			jpaTransaction.insertDataFromAPI(dataFromApi);
		};
	}
}
