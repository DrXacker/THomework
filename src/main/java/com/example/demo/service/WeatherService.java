package com.example.demo.service;

import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;
import com.example.demo.exception.WeatherBadRequestException;
import com.example.demo.exception.WeatherNotFoundException;
import com.example.demo.model.Weather;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.demo.util.ServiceUtil.isSameDay;

@Service
public class WeatherService {
    private List<Weather> weathers = new CopyOnWriteArrayList<>();
    private long nextId = 1;

    public Weather getWeatherOnCurrentDateByCity(String city) {
        return weathers.stream()
                .filter(weather -> weather.getCity().equalsIgnoreCase(city) && isSameDay(weather.getDate(), LocalDate.now()))
                .findFirst()
                .orElseThrow(() -> new WeatherNotFoundException("Weather not found"));
    }

    public Weather addWeather(Weather weather) {
        // Проверка наличия записи по городу
        Optional<Weather> existingWeather = getWeatherByCityAndDate(weather.getCity(), weather.getDate());
        if (existingWeather.isPresent()) {
            throw new WeatherBadRequestException("The record already exists");// Запись уже существует
        } else {
            createWeather(weather);
        }
        return weather;
    }

    public Weather createOrUpdateWeather(Weather weather) {
        Optional<Weather> existingWeather = getWeatherByCityAndDate(weather.getCity(), weather.getDate());
        if (existingWeather.isPresent()) {
            existingWeather.get().setTemperature(weather.getTemperature());
             // Запись успешно обновлена
        } else {
            createWeather(weather);
        }
        return weather;
    }

    // Метод для получения записи погоды по городу и дате (READ)
    private Optional<Weather> getWeatherByCityAndDate(String city, LocalDate dateTime) {
        return weathers.stream()
                .filter(weather -> weather.getCity().equalsIgnoreCase(city) && isSameDay(weather.getDate(), dateTime))
                .findFirst();
    }

    private void createWeather(Weather weather) {
        weather.setId(nextId++);
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