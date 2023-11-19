package com.example.demo.testing;

import com.example.demo.cache.WeatherCache;
import com.example.demo.model.Weather;
import com.example.demo.transaction.jpa.JpaTransaction;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class WeatherCacheTest {
    @Mock
    private JpaTransaction jpaTransaction;

    @InjectMocks
    private WeatherCache weatherCache;

    @Test
    void testGetWeatherCacheMiss() {
        // Arrange
        String city = "City1";
        Weather expectedWeather = new Weather();
        when(jpaTransaction.getWeatherDataForCity(city)).thenReturn(expectedWeather);

        // Act
        Weather result = weatherCache.getWeather(city);

        // Assert
        assertNotNull(result);
        assertEquals(expectedWeather, result);
        assertTrue(weatherCache.getCache().containsKey(city));
        verify(jpaTransaction, times(1)).getWeatherDataForCity(city);
    }

    @Test
    void testGetWeatherCacheHit() {
        // Arrange
        String city = "City2";
        Weather cachedWeather = new Weather();
        weatherCache.getCache().put(city, cachedWeather);

        // Act
        Weather result = weatherCache.getWeather(city);

        // Assert
        assertNotNull(result);
        assertEquals(cachedWeather, result);
        verify(jpaTransaction, never()).getWeatherDataForCity(anyString());
    }

    @Test
    void testUpdateWeather() {
        // Arrange
        String city = "City3";
        Weather newWeather = new Weather();

        // Act
        weatherCache.updateWeather(city, newWeather);

        // Assert
        assertTrue(weatherCache.getCache().containsKey(city));
        verify(jpaTransaction, times(1)).updateWeatherDataForCity(city, newWeather);
    }
}

