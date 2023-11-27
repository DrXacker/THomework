package com.example.demo;

import com.example.demo.model.Weather;
import com.example.demo.model.db.City;
import com.example.demo.model.db.GuideWeather;
import com.example.demo.model.db.WeatherInCity;
import com.example.demo.service.client.WeatherApiClient;
import com.example.demo.service.hibernate.CityJpaService;
import com.example.demo.service.hibernate.GuideWeatherJpaService;
import com.example.demo.service.hibernate.WeatherInCityJpaService;
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

}
