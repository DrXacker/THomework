package com.example.demo.kafka;

import com.example.demo.model.Weather;
import com.example.demo.model.db.City;
import com.example.demo.model.db.GuideWeather;
import com.example.demo.model.db.WeatherInCity;
import com.example.demo.service.hibernate.CityJpaService;
import com.example.demo.service.hibernate.GuideWeatherJpaService;
import com.example.demo.service.hibernate.WeatherInCityJpaService;
import com.example.demo.transaction.jpa.JpaTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WeatherListener {

    private final CityJpaService cityJpaService;
    private final GuideWeatherJpaService guideWeatherJpaService;
    private final WeatherInCityJpaService weatherInCityJpaService;

    @Autowired
    public WeatherListener(CityJpaService cityJpaService, GuideWeatherJpaService guideWeatherJpaService,
                           WeatherInCityJpaService weatherInCityJpaService) {
        this.cityJpaService = cityJpaService;
        this.guideWeatherJpaService = guideWeatherJpaService;
        this.weatherInCityJpaService = weatherInCityJpaService;
    }
    private static final int WINDOW_SIZE = 30;

    @KafkaListener(topics = "weather-topic", groupId = "weather-group")
    public void listenWeatherData(Weather weather) {
        insertDataFromKafkaInDB(weather);

        calculateMovingAverage(weather.getCity());

        if (weatherInCityJpaService.getCountByCityId(cityJpaService.getIdByCity(weather.getCity())) > WINDOW_SIZE)
            weatherInCityJpaService.deleteAllByCityId(cityJpaService.getIdByCity(weather.getCity()));
    }

    public void insertDataFromKafkaInDB(Weather weather){
        if(cityJpaService.getIdByCity(weather.getCity()) != null) {
            City exampleCity = new City();
            exampleCity.setName(weather.getCity());
            cityJpaService.add(exampleCity);
        }

        if(guideWeatherJpaService.getIdByDescription(weather.getDescription()) != null){
            GuideWeather exampleGW = new GuideWeather();
            exampleGW.setDescription(weather.getDescription());
            guideWeatherJpaService.add(exampleGW);
        }

        WeatherInCity exampleWC = new WeatherInCity();
        exampleWC.setDate(weather.getDateTime());
        exampleWC.setTemperature(weather.getTemperature());
        exampleWC.setCityId(cityJpaService.getIdByCity(weather.getCity()));
        exampleWC.setGuideId(guideWeatherJpaService.getIdByDescription(weather.getDescription()));
        weatherInCityJpaService.add(exampleWC);
    }

    private void calculateMovingAverage(String city) {
        List<WeatherInCity> weatherInCityList = weatherInCityJpaService.getAllByCityId(cityJpaService.getIdByCity(city));

        if (weatherInCityList.size() >= WINDOW_SIZE) {
            double sum = 0.0;
            for (WeatherInCity weather : weatherInCityList) {
                sum += weather.getTemperature();
            }
            double movingAverage = sum / WINDOW_SIZE;
            System.out.println("Moving average for " + city + ": " + movingAverage);
        }
    }
}

