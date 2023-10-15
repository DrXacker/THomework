package com.example.demo.service.hibernate;

import com.example.demo.model.GuideWeather;
import com.example.demo.repository.hibernate.HibernateGuideWeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HibernateGuideWeatherService {
    @Autowired
    private HibernateGuideWeatherRepository guideWeatherRepository;

    public List<GuideWeather> getAllWeatherTypes() {
        return guideWeatherRepository.findAll();
    }

    public GuideWeather getWeatherTypeById(Long id) {
        return guideWeatherRepository.findById(id).orElse(null);
    }

    public GuideWeather addWeatherType(GuideWeather guideWeather) {
        return guideWeatherRepository.save(guideWeather);
    }

    // Метод для обновления информации о типе погоды
    public GuideWeather updateWeatherType(GuideWeather guideWeather) {
        return guideWeatherRepository.save(guideWeather);
    }

    // Метод для удаления типа погоды по ID
    public void deleteWeatherType(Long id) {
        guideWeatherRepository.deleteById(id);
    }
}
