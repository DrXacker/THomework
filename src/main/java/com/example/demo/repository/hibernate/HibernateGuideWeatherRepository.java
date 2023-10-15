package com.example.demo.repository.hibernate;


import com.example.demo.model.GuideWeather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HibernateGuideWeatherRepository extends JpaRepository<GuideWeather,Long> {
    List<GuideWeather> findAll();

    void deleteById(Long id);
}
