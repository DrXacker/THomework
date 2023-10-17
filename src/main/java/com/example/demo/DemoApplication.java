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
import com.example.demo.model.Weather;
import com.example.demo.service.client.WeatherApiClient;
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
	public CommandLineRunner commandLineRunner(CityJpaService cityJpaService,
											   GuideWeatherJpaService guideWeatherJpaService,
											   WeatherInCityJpaService weatherInCityJpaService,
											   CityJdbcService cityJdbsService,
											   GuideWeatherJdbcService guideWeatherJdbcService,
											   WeatherInCityJdbcService weatherInCityJdbcService,
											   WeatherApiClient weatherApiClient) {
		return args -> {
			Weather dataFromApi = weatherApiClient.getCurrentWeather("London");

			City example1City = new City();
			example1City.setName(dataFromApi.getCity());
			cityJpaService.add(example1City);

			GuideWeather example1GW = new GuideWeather();
			example1GW.setDescription(dataFromApi.getDescription());
			guideWeatherJpaService.add(example1GW);

			WeatherInCity example1WC = new WeatherInCity();
			example1WC.setDate(dataFromApi.getDateTime());
			example1WC.setTemperature(dataFromApi.getTemperature());
			example1WC.setCityId(cityJpaService.getIdByCity(dataFromApi.getCity()));
			example1WC.setGuideId(guideWeatherJpaService.getIdByDescription(dataFromApi.getDescription()));
			weatherInCityJpaService.add(example1WC);

			dataFromApi = weatherApiClient.getCurrentWeather("Paris");

			City example2City = new City();
			example2City.setName(dataFromApi.getCity());
			cityJdbsService.add(example2City);

			GuideWeather example2GW = new GuideWeather();
			example2GW.setDescription(dataFromApi.getDescription());
			guideWeatherJdbcService.add(example2GW);

			WeatherInCity example2WC = new WeatherInCity();
			example2WC.setDate(dataFromApi.getDateTime());
			example2WC.setTemperature(dataFromApi.getTemperature());
			example2WC.setCityId(cityJdbsService.getIdByCity(dataFromApi.getCity()));
			example2WC.setGuideId(guideWeatherJdbcService.getIdByDescription(dataFromApi.getDescription()));
			weatherInCityJdbcService.add(example2WC);
		};
	}
}
