package com.example.demo.service.jdbc;

import com.example.demo.model.db.WeatherInCity;
import com.example.demo.repository.jdbc.WeatherInCityJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WeatherInCityJdbcService {
    @Autowired
    private WeatherInCityJdbcRepository weatherInCityRepository;

    public List<WeatherInCity> getAll() {
        return weatherInCityRepository.findAll();
    }

    public WeatherInCity getById(UUID id) {
        return weatherInCityRepository.findById(id);
    }

    public void add(WeatherInCity weather) {
        weatherInCityRepository.save(weather);
    }

    public void update(WeatherInCity weather) {
        weatherInCityRepository.update(weather);
    }

    public void delete(UUID id) {
        weatherInCityRepository.delete(id);
    }
}
