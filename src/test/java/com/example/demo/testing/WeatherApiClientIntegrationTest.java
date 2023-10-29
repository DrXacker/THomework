package com.example.demo.testing;

import com.example.demo.config.WeatherApiConfig;
import com.example.demo.exception.WeatherBadRequestException;
import com.example.demo.exception.WeatherForbiddenException;
import com.example.demo.model.Weather;
import com.example.demo.model.client.Condition;
import com.example.demo.model.client.Current;
import com.example.demo.model.client.Location;
import com.example.demo.model.client.WeatherResponse;
import com.example.demo.service.client.WeatherApiClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class WeatherApiClientIntegrationTest {

    @Autowired
    private WeatherApiClient weatherApiClient;

    @Autowired
    private WeatherApiConfig weatherApiConfig;

    @Test
    public void testGetCurrentWeather_Success() {
        String testCity = "London";

        Weather weather = weatherApiClient.getCurrentWeather(testCity);
        assertNotNull(weather);

        Location testLocation = new Location(testCity);
        Condition testCondition = new Condition("Sunny");
        Current testCurrent = new Current(LocalDateTime.now(), 0.1, testCondition);
        WeatherResponse testResp = new WeatherResponse(testLocation, testCurrent);

        assertEquals(testResp.getLocation().getName(), weather.getCity());
        assertEquals(testResp.getCurrent().getTemp_c(), weather.getTemperature(), 20.1);

        int comparisonResult = weather.getDateTime().compareTo(testResp.getCurrent().getLast_updated());
        assertTrue(comparisonResult < 0);

        assertEquals(String.class, weather.getDescription().getClass());
        assertNotNull(weather.getDescription());
    }

    @Test
    public void testGetCurrentWeather_BadRequest() {
        String testCity = "c";
        assertThrows(WeatherBadRequestException.class, () -> weatherApiClient.getCurrentWeather(testCity));
    }

    @Test
    public void testGetCurrentWeather_Forbidden(){
        String testCity = "London";

        String apikey = weatherApiConfig.getApiKey();
        weatherApiConfig.setApiKey("");

        assertThrows(WeatherForbiddenException.class, () -> weatherApiClient.getCurrentWeather(testCity));

        weatherApiConfig.setApiKey(apikey);
    }
}
