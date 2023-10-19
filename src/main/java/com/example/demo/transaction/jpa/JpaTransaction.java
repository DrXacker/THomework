package com.example.demo.transaction.jpa;

import com.example.demo.model.Weather;
import com.example.demo.model.db.City;
import com.example.demo.model.db.GuideWeather;
import com.example.demo.model.db.WeatherInCity;
import com.example.demo.service.hibernate.CityJpaService;
import com.example.demo.service.hibernate.GuideWeatherJpaService;
import com.example.demo.service.hibernate.WeatherInCityJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JpaTransaction {
    private final CityJpaService cityJpaService;
    private final GuideWeatherJpaService guideWeatherJpaService;
    private final WeatherInCityJpaService weatherInCityJpaService;

    @Autowired
    public JpaTransaction(CityJpaService cityJpaService,
                          GuideWeatherJpaService guideWeatherJpaService,
                          WeatherInCityJpaService weatherInCityJpaService) {
        this.cityJpaService = cityJpaService;
        this.guideWeatherJpaService = guideWeatherJpaService;
        this.weatherInCityJpaService = weatherInCityJpaService;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<WeatherInCity> getAllDataWeatherInCity(){
        return weatherInCityJpaService.getAll();
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public List<WeatherInCity> updateDataWeatherInCity(WeatherInCity weatherInCity){
        weatherInCityJpaService.update(weatherInCity);
        return weatherInCityJpaService.getAll();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<City> deleteDataCity(String nameCity){
        cityJpaService.delete(nameCity);
        return cityJpaService.getAll();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void insertDataFromAPI (Weather weather) {
        City exampleCity = new City();
        exampleCity.setName(weather.getCity());
        cityJpaService.add(exampleCity);

        GuideWeather exampleGW = new GuideWeather();
        exampleGW.setDescription(weather.getDescription());
        guideWeatherJpaService.add(exampleGW);

        WeatherInCity exampleWC = new WeatherInCity();
        exampleWC.setDate(weather.getDateTime());
        exampleWC.setTemperature(weather.getTemperature());
        exampleWC.setCityId(cityJpaService.getIdByCity(weather.getCity()));
        exampleWC.setGuideId(guideWeatherJpaService.getIdByDescription(weather.getDescription()));
        weatherInCityJpaService.add(exampleWC);
    }
}
