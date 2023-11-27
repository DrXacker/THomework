package com.example.demo.service.jdbc;

import com.example.demo.model.db.GuideWeather;
import com.example.demo.repository.jdbc.GuideWeatherJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GuideWeatherJdbcService {
    @Autowired
    private GuideWeatherJdbcRepository guideWeatherRepository;

    public List<GuideWeather> getAll() {
        return guideWeatherRepository.findAll();
    }

    public GuideWeather getById(UUID id) {
        return guideWeatherRepository.findById(id);
    }

    public GuideWeather getByName(String description) { return guideWeatherRepository.findByDescription(description); }

    public void add(GuideWeather guideWeather) {
        UUID existingGuideWeather = guideWeatherRepository.findIdByDescription(guideWeather.getDescription());
        if (existingGuideWeather == null) {guideWeatherRepository.save(guideWeather); }
    }

    public void update(GuideWeather guideWeather) { guideWeatherRepository.update(guideWeather); }

    public void delete(UUID id) { guideWeatherRepository.delete(id); }

    public UUID getIdByDescription(String description) { return guideWeatherRepository.findIdByDescription(description); }
}
