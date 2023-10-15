package com.example.demo.service.jdbc;

import com.example.demo.model.City;
import com.example.demo.repository.jdbc.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JdbcCityService{
    @Autowired
    private CityRepository cityRepository;

    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    public City getCityById(Long id) {
        return cityRepository.findById(id);
    }

    public void addCity(City city) {
        cityRepository.save(city);
    }

    public void updateCity(City city) {
        cityRepository.update(city);
    }

    public void deleteCity(Long id) {
        cityRepository.delete(id);
    }
}
