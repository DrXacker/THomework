package com.example.demo.service.hibernate;

import com.example.demo.model.db.City;
import com.example.demo.repository.hibernate.CityJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CityJpaService {
    @Autowired
    private CityJpaRepository cityRepository;

    public List<City> getAll() {
        return cityRepository.findAll();
    }

    public Optional<City> getById(UUID id) { return cityRepository.findById(id); }

    public Optional<City> getByName(String city) { return cityRepository.findByName(city); }

    public City add(City city) {
        return cityRepository.save(city);
    }

    public void update(City city) {
        cityRepository.save(city);
    }

    public void delete(String nameCity) { cityRepository.deleteCitiesByName(nameCity); }

    public UUID getIdByCity(String city) { return cityRepository.findIdByCity(city); }
}
