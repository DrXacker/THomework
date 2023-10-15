package com.example.demo.service.jdbc;

import com.example.demo.model.WeatherInCity;
import com.example.demo.repository.jdbc.WeatherInCityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherInCityService {
    private WeatherInCityRepository weatherInCityRepository;

    public List<WeatherInCity> getAllWeatherInCity() {
        return weatherInCityRepository.findAll();
    }

    public WeatherInCity getWeatherInCityById(Long id) {
        return weatherInCityRepository.findById(id);
    }

    public void addWeatherInCity(WeatherInCity weather) {
        weatherInCityRepository.save(weather);
    }

    public void updateWeatherInCity(WeatherInCity weather) {
        weatherInCityRepository.update(weather);
    }

    public void deleteWeatherInCity(Long id) {
        weatherInCityRepository.delete(id);
    }
}
