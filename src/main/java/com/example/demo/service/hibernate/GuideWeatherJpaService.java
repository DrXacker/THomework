package com.example.demo.service.hibernate;

import com.example.demo.model.db.GuideWeather;
import com.example.demo.repository.hibernate.GuideWeatherJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GuideWeatherJpaService {
    @Autowired
    private GuideWeatherJpaRepository guideWeatherRepository;

    public List<GuideWeather> getAllWeatherTypes() {
        return guideWeatherRepository.findAll();
    }

    public GuideWeather getWeatherTypeById(UUID id) {
        return guideWeatherRepository.findById(id).orElse(null);
    }

    public GuideWeather addWeatherType(GuideWeather guideWeather) {
        return guideWeatherRepository.save(guideWeather);
    }

    public GuideWeather updateWeatherType(GuideWeather guideWeather) { return guideWeatherRepository.save(guideWeather); }

    public void deleteWeatherType(UUID id) {
        guideWeatherRepository.deleteById(id);
    }
}
