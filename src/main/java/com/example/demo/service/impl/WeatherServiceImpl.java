package com.example.demo.service.impl;

import com.example.demo.exception.WeatherBadRequestException;
import com.example.demo.exception.WeatherNotFoundException;
import com.example.demo.model.Weather;
import com.example.demo.service.WeatherService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static com.example.demo.util.ServiceUtil.isSameDay;

@Service
public class WeatherServiceImpl implements WeatherService {
    private final List<Weather> weathers = new ArrayList<>();
    private long nextId = 1;

    @Override
    public Weather getWeatherOnCurrentDateByCity(String city) {
        return weathers.stream()
                .filter(weather -> weather.getCity().equalsIgnoreCase(city) && isSameDay(weather.getDateTime(), LocalDateTime.now()))
                .findFirst()
                .orElseThrow(() -> new WeatherNotFoundException("Weather not found"));
    }

    // Метод для получения записи погоды по городу и дате (READ)
    private Optional<Weather> getWeatherByCityAndDate(String city, LocalDateTime dateTime) {
        return weathers.stream()
                .filter(weather -> weather.getCity().equalsIgnoreCase(city) && isSameDay(weather.getDateTime(), dateTime))
                .findFirst();
    }

    @Override
    public Weather addWeather(Weather weather) {
        // Проверка наличия записи по городу
        Optional<Weather> existingWeather = getWeatherByCityAndDate(weather.getCity(), weather.getDateTime());
        if (existingWeather.isPresent()) {
            throw new WeatherBadRequestException("The record already exists");// Запись уже существует
        }
        else {
            createWeather(weather);
        }
        return weather;
    }

    private void createWeather(Weather weather) {
        weather.setId(nextId++);
        weathers.add(weather);
    }

    @Override
    public Weather createOrUpdateWeather(Weather weather) {
        Optional<Weather> existingWeather = getWeatherByCityAndDate(weather.getCity(), weather.getDateTime());
        if (existingWeather.isPresent()) {
            existingWeather.get().setTemperature(weather.getTemperature());
             // Запись успешно обновлена
        }
        else {
            createWeather(weather);
        }
        return weather;
    }

    @Override
    public void deleteWeather(String city) {
        Iterator<Weather> iterator = weathers.iterator();
        boolean flag = false;
        while (iterator.hasNext()) {
            Weather weather = iterator.next();
            if (weather.getCity().equalsIgnoreCase(city)) {
                iterator.remove();
                flag = true;
            }
        }
        if (!flag)
            throw new WeatherNotFoundException("Weather not found");
    }
}