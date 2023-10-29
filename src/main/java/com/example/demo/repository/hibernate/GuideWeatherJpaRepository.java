package com.example.demo.repository.hibernate;


import com.example.demo.model.db.GuideWeather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface GuideWeatherJpaRepository extends JpaRepository<GuideWeather, UUID> {

    @Query("SELECT gw.id FROM GuideWeather gw WHERE gw.description = :description")
    UUID findIdByDescription(@Param("description") String description);

    void deleteById(UUID id);
}
