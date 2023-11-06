package com.example.demo.testing.testcontainers;

import com.example.demo.model.db.GuideWeather;
import com.example.demo.repository.hibernate.GuideWeatherJpaRepository;
import com.example.demo.service.hibernate.GuideWeatherJpaService;
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

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@Testcontainers
@Import(GuideWeatherJpaService.class)
public class GuideWeatherJpaServiceTest {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("TinkoffHome")
            .withUsername("postgres")
            .withPassword("qwer");

    @Autowired
    private GuideWeatherJpaService guideWeatherService;

    @MockBean
    private GuideWeatherJpaRepository guideWeatherRepository;

    @DynamicPropertySource
    static void setupProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Test
    public void testGetById() {
        UUID guideWeatherId = UUID.randomUUID();
        GuideWeather guideWeather = new GuideWeather(guideWeatherId, "Test Description");
        Mockito.when(guideWeatherRepository.findById(guideWeatherId)).thenReturn(Optional.of(guideWeather));

        GuideWeather result = guideWeatherService.getById(guideWeatherId);

        Mockito.verify(guideWeatherRepository).findById(guideWeatherId);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(guideWeather, result);
    }

    @Test
    public void testAdd() {
        UUID generatedId = UUID.randomUUID();
        GuideWeather guideWeather = new GuideWeather(generatedId,"New Description");
        Mockito.when(guideWeatherRepository.findIdByDescription(guideWeather.getDescription())).thenReturn(null);
        Mockito.when(guideWeatherRepository.save(guideWeather)).thenReturn(new GuideWeather(generatedId, "New Description"));

        guideWeatherService.add(guideWeather);

        Mockito.verify(guideWeatherRepository).findIdByDescription(guideWeather.getDescription());
        Mockito.verify(guideWeatherRepository).save(guideWeather);
    }

    @Test
    public void testAddAlreadyExisting() {
        UUID existingId = UUID.randomUUID();
        GuideWeather guideWeather = new GuideWeather(existingId,"Existing Description");
        Mockito.when(guideWeatherRepository.findIdByDescription(guideWeather.getDescription())).thenReturn(existingId);

        guideWeatherService.add(guideWeather);

        Mockito.verify(guideWeatherRepository).findIdByDescription(guideWeather.getDescription());
        Mockito.verify(guideWeatherRepository, Mockito.never()).save(guideWeather);
    }

    @Test
    public void testUpdate() {
        UUID guideWeatherId = UUID.randomUUID();
        GuideWeather guideWeather = new GuideWeather(guideWeatherId, "Existing Description");

        guideWeatherService.update(guideWeather);

        Mockito.verify(guideWeatherRepository).save(guideWeather);
    }

    @Test
    public void testDelete() {
        UUID guideWeatherId = UUID.randomUUID();
        GuideWeather guideWeather = new GuideWeather(guideWeatherId, "Existing Description");

        guideWeatherService.delete(guideWeatherId);

        Mockito.verify(guideWeatherRepository).deleteById(guideWeatherId);
    }

    @Test
    public void testGetIdByDescription() {
        String description = "DescriptionToFind";
        UUID guideWeatherId = UUID.randomUUID();
        Mockito.when(guideWeatherRepository.findIdByDescription(description)).thenReturn(guideWeatherId);

        UUID result = guideWeatherService.getIdByDescription(description);

        Mockito.verify(guideWeatherRepository).findIdByDescription(description);
        Assertions.assertEquals(guideWeatherId, result);
    }
}

