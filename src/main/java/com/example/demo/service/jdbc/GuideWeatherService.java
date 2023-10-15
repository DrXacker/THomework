package com.example.demo.service.jdbc;

import com.example.demo.model.GuideWeather;
import com.example.demo.repository.jdbc.GuideWeatherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuideWeatherService {
    private GuideWeatherRepository weatherTypeRepository;

    public List<GuideWeather> getAllWeatherTypes() {
        return weatherTypeRepository.findAll();
    }

    public GuideWeather getWeatherTypeById(Long id) {
        return weatherTypeRepository.findById(id);
    }

    public GuideWeather getWeatherTypeByName(String name) {
        return weatherTypeRepository.findByName(name);
    }

    public void addWeatherType(GuideWeather guideWeather) {
        weatherTypeRepository.save(guideWeather);
    }

    public void updateWeatherType(GuideWeather guideWeather) {
        weatherTypeRepository.update(guideWeather);
    }

    public void deleteWeatherType(Long id) {
        weatherTypeRepository.delete(id);
    }
}
