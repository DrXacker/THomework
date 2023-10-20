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

    public List<GuideWeather> getAll() {
        return guideWeatherRepository.findAll();
    }

    public GuideWeather getById(UUID id) {
        return guideWeatherRepository.findById(id).orElse(null);
    }

    public void add(GuideWeather guideWeather) {
        UUID existingGuideWeather = guideWeatherRepository.findIdByDescription(guideWeather.getDescription());
        if (existingGuideWeather == null) {
        guideWeatherRepository.save(guideWeather);
        }
    }

    public void update(GuideWeather guideWeather) { guideWeatherRepository.save(guideWeather); }

    public void delete(UUID id) {
        guideWeatherRepository.deleteById(id);
    }

    public UUID getIdByDescription(String city) { return guideWeatherRepository.findIdByDescription(city); }
}
