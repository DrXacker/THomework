package com.example.demo.repository.hibernate;

import com.example.demo.model.db.WeatherInCity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.UUID;


public interface WeatherInCityJpaRepository extends JpaRepository<WeatherInCity, UUID> {

    void deleteById(UUID id);

    void deleteByTemperature(LocalDateTime localDateTime);

    WeatherInCity findByCityId(UUID id);

    @Query("SELECT c.id FROM WeatherInCity c WHERE c.cityId = :city_id")
    UUID findIdByCityId(@Param("city_id") UUID cityId);
}
