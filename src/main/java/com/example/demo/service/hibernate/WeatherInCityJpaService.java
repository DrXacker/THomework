package com.example.demo.service.hibernate;

import com.example.demo.model.db.WeatherInCity;
import com.example.demo.repository.hibernate.WeatherInCityJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WeatherInCityJpaService {

    @Autowired
    private WeatherInCityJpaRepository weatherInCityRepository;

    public List<WeatherInCity> getAllWeather() {
        return weatherInCityRepository.findAll();
    }

    public WeatherInCity getWeatherById(UUID id) {
        return weatherInCityRepository.findById(id).orElse(null);
    }

    public WeatherInCity addWeather(WeatherInCity weatherInCity) {
        return weatherInCityRepository.save(weatherInCity);
    }

    public WeatherInCity updateWeather(WeatherInCity weatherInCity) { return weatherInCityRepository.save(weatherInCity); }

    public void deleteWeather(UUID id) {
        weatherInCityRepository.deleteById(id);
    }
}
