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

    public List<City> getAll() {
        return cityRepository.findAll();
    }

    public City getById(UUID id) {
        return cityRepository.findById(id);
    }

    public void add(City city) { cityRepository.save(city); }

    public void update(City city) {
        cityRepository.update(city);
    }

    public void delete(String nameCity) { cityRepository.delete(nameCity); }

    public UUID getIdByCity(String name) { return cityRepository.findIdByName(name); }
}
