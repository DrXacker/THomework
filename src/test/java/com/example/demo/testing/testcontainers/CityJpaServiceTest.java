package com.example.demo.testing.testcontainers;

import com.example.demo.model.db.City;
import com.example.demo.repository.hibernate.CityJpaRepository;
import com.example.demo.service.hibernate.CityJpaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@Testcontainers
@Import(CityJpaService.class)
public class CityJpaServiceTest {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("TinkoffHome")
            .withUsername("postgres")
            .withPassword("qwer");

    @Autowired
    private CityJpaService cityService;

    @SpyBean
    private CityJpaRepository cityRepository;

    @DynamicPropertySource
    static void setupProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Test
    public void testGetById() {
        UUID cityId = UUID.randomUUID();
        City city = new City(cityId, "Test City");
        Mockito.when(cityRepository.findById(cityId)).thenReturn(Optional.of(city));

        Optional<City> result = cityService.getById(cityId);

        Mockito.verify(cityRepository).findById(cityId); // Проверяем, что метод findById был вызван
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(city, result.get());
    }

    @Test
    public void testAdd() {
        UUID generatedId = UUID.randomUUID();
        City city = new City(generatedId,"New City");
        Mockito.when(cityRepository.save(city)).thenReturn(new City(generatedId, "New City"));

        City addedCity = cityService.add(city);

        Mockito.verify(cityRepository).save(city); // Проверяем, что метод save был вызван
        Assertions.assertEquals(generatedId, addedCity.getId());
        Assertions.assertEquals("New City", addedCity.getName());
    }

    @Test
    public void testUpdate() {
        UUID cityId = UUID.randomUUID();
        City city = new City(cityId, "Existing City");

        cityService.update(city);

        Mockito.verify(cityRepository).save(city); // Проверяем, что метод save был вызван
    }

    @Test
    public void testDelete() {
        String cityName = "CityToDelete";

        cityService.delete(cityName);

        Mockito.verify(cityRepository).deleteCitiesByName(cityName); // Проверяем, что метод deleteByName был вызван
    }

    @Test
    public void testGetIdByCity() {
        String cityName = "CityToFind";
        UUID cityId = UUID.randomUUID();
        Mockito.when(cityRepository.findIdByCity(cityName)).thenReturn(cityId);

        UUID result = cityService.getIdByCity(cityName);

        Mockito.verify(cityRepository).findIdByCity(cityName); // Проверяем, что метод findIdByCity был вызван
        Assertions.assertEquals(cityId, result);
    }
}

