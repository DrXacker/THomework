package com.example.demo.repository.hibernate;

import com.example.demo.model.db.WeatherInCity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface WeatherInCityJpaRepository extends JpaRepository<WeatherInCity, UUID> {
}
