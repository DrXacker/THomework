package com.example.demo.testing.testcontainers;

import com.example.demo.model.db.City;
import com.example.demo.model.db.GuideWeather;
import com.example.demo.model.db.WeatherInCity;
import com.example.demo.repository.hibernate.WeatherInCityJpaRepository;
import com.example.demo.service.hibernate.CityJpaService;
import com.example.demo.service.hibernate.GuideWeatherJpaService;
import com.example.demo.service.hibernate.WeatherInCityJpaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@Testcontainers
@Import(WeatherInCityJpaService.class)
public class WeatherInCityJpaServiceTest {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("TinkoffHome")
            .withUsername("postgres")
            .withPassword("qwer");

    @Autowired
    private WeatherInCityJpaService weatherInCityService;

    @Autowired
    private GuideWeatherJpaService guideWeatherJpaService;

    @Autowired
    private CityJpaService cityJpaService;

    @MockBean
    private WeatherInCityJpaRepository weatherInCityRepository;

    @DynamicPropertySource
    static void setupProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Test
    public void testGetById() {
        UUID generatedId = UUID.randomUUID();
        WeatherInCity weatherInCity = new WeatherInCity();
        weatherInCity.setId(generatedId);
        Mockito.when(weatherInCityRepository.findById(generatedId)).thenReturn(Optional.of(weatherInCity));

        WeatherInCity result = weatherInCityService.getById(generatedId);

        Mockito.verify(weatherInCityRepository).findById(generatedId);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(weatherInCity, result);
    }

    @Test
    public void testAdd() {
        UUID generatedId = UUID.randomUUID();
        WeatherInCity weatherInCity = new WeatherInCity();
        weatherInCity.setId(generatedId);
        Mockito
                .when(weatherInCityRepository.save(weatherInCity))
                .then(invocation -> {
                    WeatherInCity savedWeatherInCity = invocation.getArgument(0);
                    savedWeatherInCity.setId(generatedId);
                    return savedWeatherInCity;
                });

        WeatherInCity addedWeatherInCity = weatherInCityService.add(weatherInCity);

        Mockito.verify(weatherInCityRepository).save(weatherInCity);
        Assertions.assertEquals(generatedId, addedWeatherInCity.getId());
    }

    @Test
    public void testUpdate() {
        UUID generatedId = UUID.randomUUID();
        WeatherInCity weatherInCity = new WeatherInCity();
        weatherInCity.setId(generatedId);

        weatherInCityService.update(weatherInCity);

        Mockito.verify(weatherInCityRepository).save(weatherInCity);
    }

    @Test
    public void testDelete() {
        UUID generatedId = UUID.randomUUID();

        City city = new City(generatedId, "test");
        cityJpaService.add(city);

        GuideWeather guideWeather = new GuideWeather(generatedId, "test");
        guideWeatherJpaService.add(guideWeather);

        WeatherInCity weatherInCity = new WeatherInCity();
        weatherInCity.setId(generatedId);
        weatherInCity.setTemperature(20.5);
        weatherInCity.setCityId(cityJpaService.getIdByCity("test"));
        weatherInCity.setGuideId(guideWeatherJpaService.getIdByDescription("test"));
        weatherInCity.setDate(LocalDateTime.now());

        weatherInCityService.add(weatherInCity);

        weatherInCityService.delete(generatedId);

        Mockito.verify(weatherInCityRepository).deleteById(generatedId);
    }
}

