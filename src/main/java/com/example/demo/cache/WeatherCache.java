package com.example.demo.cache;

import com.example.demo.exception.WeatherNotFoundException;
import com.example.demo.model.Weather;
import com.example.demo.transaction.jpa.JpaTransaction;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;

@Service
@Getter
@Setter
public class WeatherCache {

    @Value("${cache.course.size}")
    private int maxSize;

    @Autowired
    private JpaTransaction jpaTransaction;

    private final Map<String, Weather> cache;
    private final Lock lock;

    public WeatherCache() {
        this.cache = new LinkedHashMap<>(this.maxSize, 0.75f, true);
        this.lock = new ReentrantLock();
    }

    public Weather getWeather(String city) {
        lock.lock();
        try {
            return cache.merge(city, fetchAndUpdateWeather(city), (existingWeather, newWeather) -> existingWeather);
        } finally {
            lock.unlock();
        }
    }

    private Weather fetchAndUpdateWeather(String city) {
        Weather weather = fetchWeatherFromDatabase(city);
        if (weather == null) {
            throw new WeatherNotFoundException("Weather data not found for city: " + city);
        }
        updateCache(city, weather);
        return weather;
    }

    private Weather fetchWeatherFromDatabase(String city) {
        return jpaTransaction.getWeatherDataForCity(city);
    }

    public void updateWeather(String city, Weather newWeather) {
        lock.lock();
        try {
            updateCache(city, newWeather);
            updateDatabase(city, newWeather);
        } finally {
            lock.unlock();
        }
    }

    private void updateCache(String city, Weather weather) {
        if (cache.size() >= maxSize) {
            evictOldestEntry();
        }
        cache.put(city, weather);
    }

    private void evictOldestEntry() {
        if (!cache.isEmpty()) {
            Map.Entry<String, Weather> eldestEntry = cache.entrySet().iterator().next();
            cache.remove(eldestEntry.getKey());
        }
    }

    private void updateDatabase(String city, Weather weather) {
        jpaTransaction.updateWeatherDataForCity(city, weather);
    }
}