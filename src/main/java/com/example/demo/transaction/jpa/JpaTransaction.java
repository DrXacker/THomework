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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<Weather> getAllWeatherData() {
        List<WeatherInCity> weatherInCities = weatherInCityJpaService.getAll();
        List<Weather> weatherDataList = new ArrayList<>();

        for (WeatherInCity weatherInCity : weatherInCities) {
            City city = cityJpaService.getById(weatherInCity.getCityId()).orElse(null);
            if (city != null) {
                GuideWeather guideWeather = guideWeatherJpaService.getById(weatherInCity.getGuideId());

                Weather weather = new Weather();
                weather.setId(weatherInCity.getCityId());
                weather.setCity(city.getName());
                weather.setTemperature(weatherInCity.getTemperature());
                weather.setDateTime(weatherInCity.getDate());
                weather.setDescription(guideWeather != null ? guideWeather.getDescription() : null);

                weatherDataList.add(weather);
            }
        }

        return weatherDataList;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Weather getWeatherDataForCity(String name) {
        City city = cityJpaService.getByName(name).orElse(null);
        if (city == null) { return null; }

        WeatherInCity weatherInCity = weatherInCityJpaService.getByCityId(city.getId());
        GuideWeather guideWeather = guideWeatherJpaService.getById(weatherInCity.getGuideId());

        Weather weather = new Weather();
        weather.setId(city.getId());
        weather.setCity(city.getName());
        weather.setTemperature(weatherInCity.getTemperature());
        weather.setDateTime(weatherInCity.getDate());
        weather.setDescription(guideWeather.getDescription());

        return weather;
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

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void updateWeatherDataForCity (String city, Weather weather) {
        UUID uuidCity = cityJpaService.getIdByCity(city);

        GuideWeather exampleGW = new GuideWeather();
        exampleGW.setDescription(weather.getDescription());
        guideWeatherJpaService.add(exampleGW);

        UUID uuidWeatherInCity = weatherInCityJpaService.getIdByCityId(uuidCity);

        WeatherInCity exampleWC = new WeatherInCity();
        exampleWC.setId(uuidWeatherInCity);
        exampleWC.setDate(weather.getDateTime());
        exampleWC.setTemperature(weather.getTemperature());
        exampleWC.setCityId(cityJpaService.getIdByCity(city));
        exampleWC.setGuideId(guideWeatherJpaService.getIdByDescription(weather.getDescription()));
        weatherInCityJpaService.update(exampleWC);
    }
}
