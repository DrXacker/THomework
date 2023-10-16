package com.example.demo.service.jdbc;

import com.example.demo.model.db.City;
import com.example.demo.repository.jdbc.CityJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CityJdbcService {
    @Autowired
    private CityJdbcRepository cityRepository;

    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    public City getCityById(UUID id) {
        return cityRepository.findById(id);
    }

    public void addCity(City city) { cityRepository.save(city); }

    public void updateCity(City city) {
        cityRepository.update(city);
    }

    public void deleteCity(UUID id) {
        cityRepository.delete(id);
    }

    public UUID getIdByCity(String name) { return cityRepository.findIdByName(name); }
}
