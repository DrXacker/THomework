package com.example.demo;

import com.example.demo.model.db.City;
import com.example.demo.model.db.GuideWeather;
import com.example.demo.model.db.WeatherInCity;
import com.example.demo.service.hibernate.CityJpaService;
import com.example.demo.service.hibernate.GuideWeatherJpaService;
import com.example.demo.service.hibernate.WeatherInCityJpaService;
import com.example.demo.service.jdbc.CityJdbcService;
import com.example.demo.service.jdbc.GuideWeatherJdbcService;
import com.example.demo.service.jdbc.WeatherInCityJdbcService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.example.demo.model.Weather;
import com.example.demo.service.client.WeatherApiClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) throws JsonProcessingException {
		ApplicationContext context = SpringApplication.run(DemoApplication.class, args);
//		WeatherApiClient clientService = context.getBean(WeatherApiClient.class);
//		Weather q = clientService.getCurrentWeather("London");
//		System.out.println(q);
	}

	@Bean
	public CommandLineRunner commandLineRunner(CityJdbcService cityService,
											   GuideWeatherJdbcService guideWeatherService,
											   WeatherInCityJdbcService weatherInCityService,
											   WeatherApiClient weatherApiClient){
		return args -> {
			Weather dataFromApi = weatherApiClient.getCurrentWeather("London");

			City example1City = new City();
			example1City.setName(dataFromApi.getCity());
			cityService.addCity(example1City);

			GuideWeather example1GW = new GuideWeather();
			example1GW.setDescription(dataFromApi.getDescription());
			guideWeatherService.addGuideWeather(example1GW);

			WeatherInCity example1WC = new WeatherInCity();
			example1WC.setDate(dataFromApi.getDateTime());
			example1WC.setTemperature(dataFromApi.getTemperature());
			example1WC.setCityId(cityService.getIdByCity(dataFromApi.getCity()));
			example1WC.setGuideId(guideWeatherService.getIdByDescription(dataFromApi.getDescription()));
			weatherInCityService.addWeatherInCity(example1WC);
		};
	}
}
