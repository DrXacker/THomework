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

    public List<WeatherInCity> getAll() {
        return weatherInCityRepository.findAll();
    }

    public WeatherInCity getById(UUID id) {
        return weatherInCityRepository.findById(id).orElse(null);
    }

    public WeatherInCity getByCityId(UUID id) { return weatherInCityRepository.findByCityId(id); }

    public WeatherInCity add(WeatherInCity weatherInCity) {
        return weatherInCityRepository.save(weatherInCity);
    }

    public WeatherInCity update(WeatherInCity weatherInCity) { return weatherInCityRepository.save(weatherInCity); }

    public void delete(UUID id) {
        weatherInCityRepository.deleteById(id);
    }
}
