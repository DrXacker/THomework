package com.example.demo.service;

import com.example.demo.model.Weather;

public interface WeatherService {
    Weather getWeatherOnCurrentDateByCity(String city);
    Weather addWeather(Weather weather);
    Weather createOrUpdateWeather(Weather weather);
    void deleteWeather(String city);
}
