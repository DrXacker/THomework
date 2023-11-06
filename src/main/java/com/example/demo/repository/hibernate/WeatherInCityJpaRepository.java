package com.example.demo.repository.hibernate;

import com.example.demo.model.db.WeatherInCity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.UUID;


public interface WeatherInCityJpaRepository extends JpaRepository<WeatherInCity, UUID> {

    void deleteById(UUID id);

    void deleteByTemperature(LocalDateTime localDateTime);

    WeatherInCity findByCityId(UUID id);
}
