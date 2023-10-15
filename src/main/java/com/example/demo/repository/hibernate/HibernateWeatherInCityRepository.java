package com.example.demo.repository.hibernate;

import com.example.demo.model.WeatherInCity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HibernateWeatherInCityRepository extends JpaRepository<WeatherInCity,Long> {

    List<WeatherInCity> findAll();
    void deleteById(Long id);
}
