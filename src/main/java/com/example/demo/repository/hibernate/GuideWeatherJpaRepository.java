package com.example.demo.repository.hibernate;


import com.example.demo.model.db.GuideWeather;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GuideWeatherJpaRepository extends JpaRepository<GuideWeather, UUID> {
}
