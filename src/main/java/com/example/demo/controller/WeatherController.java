package com.example.demo.controller;

import com.example.demo.model.Weather;
import com.example.demo.service.client.WeatherApiClient;
import com.example.demo.service.WeatherService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {
    private final WeatherService weatherService;
    private final WeatherApiClient weatherApiClient;

    public WeatherController(WeatherService weatherService, WeatherApiClient weatherApiClient) {
        this.weatherService = weatherService;
        this.weatherApiClient = weatherApiClient;
    }

    @GetMapping("/{city}")
    public Weather getWeatherOnCurrentDateByCity(@PathVariable String city) {
        return weatherService.getWeatherOnCurrentDateByCity(city);
    }

    @PostMapping("/")
    public Weather addWeather(@RequestBody Weather weather) {
        return weatherService.addWeather(weather);
    }

    @PutMapping("/")
    public Weather updateWeather(@RequestBody Weather weather) {
        return weatherService.createOrUpdateWeather(weather);
    }

    @DeleteMapping("/{city}")
    public void deleteWeather(@PathVariable String city) {
        weatherService.deleteWeather(city);
    }

    @GetMapping("/weather")
    public Weather getWeatherApi(@RequestParam String city) throws JsonProcessingException {
        return weatherApiClient.getCurrentWeather(city);
    }
}
