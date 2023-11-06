package com.example.demo.service;
import com.example.demo.exception.WeatherBadRequestException;
import com.example.demo.exception.WeatherNotFoundException;

import com.example.demo.model.Weather;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.example.demo.util.ServiceUtil.isSameDay;

@Service
public class WeatherService {
    private List<Weather> weathers = new CopyOnWriteArrayList<>();

    public Weather getWeatherOnCurrentDateByCity(String city) {
        return weathers.stream()
                .filter(weather -> weather.getCity().equalsIgnoreCase(city) && isSameDay(weather.getDateTime(), LocalDateTime.now()))
                .findFirst()
                .orElseThrow(() -> new WeatherNotFoundException("Weather not found"));
    }

    public Weather addWeather(Weather weather) {
        // Проверка наличия записи по городу
        Optional<Weather> existingWeather = getWeatherByCityAndDate(weather.getCity(), weather.getDateTime());
        if (existingWeather.isPresent()) {
            throw new WeatherBadRequestException("The record already exists");// Запись уже существует
        } else {
            createWeather(weather);
        }
        return weather;
    }

    public Weather createOrUpdateWeather(Weather weather) {
        Optional<Weather> existingWeather = getWeatherByCityAndDate(weather.getCity(), weather.getDateTime());
        if (existingWeather.isPresent()) {
            existingWeather.get().setTemperature(weather.getTemperature());
            // Запись успешно обновлена
        } else {
            createWeather(weather);
        }
        return weather;
    }

    // Метод для получения записи погоды по городу и дате (READ)
    private Optional<Weather> getWeatherByCityAndDate(String city, LocalDateTime dateTime) {
        return weathers.stream()
                .filter(weather -> weather.getCity().equalsIgnoreCase(city) && isSameDay(weather.getDateTime(), dateTime))
                .findFirst();
    }

    private void createWeather(Weather weather) {
        weather.setId(UUID.randomUUID());
        weathers.add(weather);
    }

    public void deleteWeather(String city) {
        // Создание нового списка без элементов, которые нужно удалить
        List<Weather> updatedWeathers = weathers.stream()
                .filter(weather -> !weather.getCity().equals(city))
                .toList();

        if (weathers.size() == updatedWeathers.size()) {
            throw new WeatherNotFoundException("City:" + city + " - not found");
        }

        // Переприсвоение переменной weathers новым списком
        weathers = updatedWeathers;
    }
}