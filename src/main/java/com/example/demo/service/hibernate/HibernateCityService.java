package com.example.demo.service.hibernate;

import com.example.demo.model.City;
import com.example.demo.repository.hibernate.HibernateCityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HibernateCityService {
    @Autowired
    private HibernateCityRepository cityRepository;

    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    public Optional<City> getCityById(Long id) {
        return cityRepository.findById(id);
    }

    public City addCity(City city) {
        return cityRepository.save(city);
    }

    public void updateCity(City city) {
        cityRepository.save(city);
    }

    public void deleteCity(Long id) {
        cityRepository.deleteById(id);
    }
}
