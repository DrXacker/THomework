package com.example.demo.controllers;

import com.example.demo.model.Weather;
import com.example.demo.service.WeatherService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {
    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
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
}