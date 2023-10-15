package com.example.demo.service.hibernate;

import com.example.demo.model.WeatherInCity;
import com.example.demo.repository.hibernate.HibernateWeatherInCityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HibernateWeatherInCityService {

    @Autowired
    private HibernateWeatherInCityRepository weatherInCityRepository;

    public List<WeatherInCity> getAllWeather() {
        return weatherInCityRepository.findAll();
    }

    public WeatherInCity getWeatherById(Long id) {
        return weatherInCityRepository.findById(id).orElse(null);
    }

    public WeatherInCity addWeather(WeatherInCity weatherInCity) {
        return weatherInCityRepository.save(weatherInCity);
    }

    public WeatherInCity updateWeather(WeatherInCity weatherInCity) {
        return weatherInCityRepository.save(weatherInCity);
    }

    public void deleteWeather(Long id) {
        weatherInCityRepository.deleteById(id);
    }
}
