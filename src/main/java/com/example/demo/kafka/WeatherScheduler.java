package com.example.demo.kafka;

import com.example.demo.model.Weather;
import com.example.demo.service.client.WeatherApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WeatherScheduler {

    @Autowired
    private WeatherApiClient weatherApiClient;
    private final WeatherProducer weatherProducer;

    private int currentCityIndex = 0;

    public WeatherScheduler(WeatherApiClient weatherApiClient, WeatherProducer weatherProducer) {
        this.weatherApiClient = weatherApiClient;
        this.weatherProducer = weatherProducer;
    }

    @Scheduled(cron = "0 */3 * * * ?")
    public void fetchWeatherData() {
        String[] cities = {"Moscow", "Saint Petersburg", "Voronezh", "Liski", "Orel"};
        String currentCity = cities[currentCityIndex];
        Weather  weather = weatherApiClient.getCurrentWeather(currentCity);
        weatherProducer.produceWeatherData(weather);

        currentCityIndex = (currentCityIndex + 1) % cities.length;
    }
}