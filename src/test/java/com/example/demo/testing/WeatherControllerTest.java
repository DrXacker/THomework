package com.example.demo.testing;

import com.example.demo.controller.WeatherController;
import com.example.demo.model.Weather;
import com.example.demo.service.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootTest
public class WeatherControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private WeatherService weatherService;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new WeatherController(weatherService)).build();

        Weather weather = new Weather();
        weather.setCity("TestCity");
        weather.setTemperature(25.0);
        weather.setId(UUID.randomUUID());
        weather.setDateTime(LocalDateTime.now());
        Mockito.when(weatherService.getWeatherOnCurrentDateByCity("TestCity")).thenReturn(weather);
    }

    @Test
    public void testGetWeatherOnCurrentDateByCity() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/weather/TestCity"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.city").value("TestCity"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.temperature").value(25.0));
    }

    @Test
    public void testAddWeather() throws Exception {
        Weather newWeather = new Weather();
        newWeather.setCity("NewCity");
        newWeather.setTemperature(30.0);
        newWeather.setId(UUID.randomUUID());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/weather/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"city\":\"NewCity\",\"temperature\":30.0}")
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }
}

