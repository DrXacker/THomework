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

    public List<GuideWeather> getAllWeatherTypes() {
        return guideWeatherRepository.findAll();
    }

    public GuideWeather getGuideWeatherTypeById(UUID id) {
        return guideWeatherRepository.findById(id);
    }

    public GuideWeather getGuideWeatherByName(String description) { return guideWeatherRepository.findByDescription(description); }

    public void addGuideWeather(GuideWeather guideWeather) {
        guideWeatherRepository.save(guideWeather);
    }

    public void updateGuideWeather(GuideWeather guideWeather) { guideWeatherRepository.update(guideWeather); }

    public void deleteGuideWeather(UUID id) {
        guideWeatherRepository.delete(id);
    }

    public UUID getIdByDescription(String description) { return guideWeatherRepository.findIdByDescription(description); }
}
