package com.example.demo.service;

import com.example.demo.config.WeatherApiConfig;
import com.example.demo.exception.*;
import com.example.demo.model.ApiErrorResponse;
import com.example.demo.model.Weather;
import com.example.demo.model.WeatherResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.ratelimiter.RateLimiter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherApiClient {

    private final RestTemplate restTemplate;
    private final RateLimiter rateLimiter;
    private final WeatherApiConfig weatherApiConfig;

    public WeatherApiClient(@Qualifier("restTemplate") RestTemplate restTemplate, @Qualifier("rateLimiter") RateLimiter rateLimiter, WeatherApiConfig weatherApiConfig) {
        this.restTemplate = restTemplate;
        this.rateLimiter = rateLimiter;
        this.weatherApiConfig = weatherApiConfig;
    }

    public Weather getCurrentWeather(String city) throws JsonProcessingException {
        try {
            return rateLimiter.executeSupplier(() -> {
                //String apiUrl = weatherApiConfig.getBaseUrl() + "/v1/current.json?key=" + weatherApiConfig.getApiKey() + "&q=" + city + "&aqi=no";
                String apiUrl = weatherApiConfig.getBaseUrl() + "/v1/current.json?key=" + weatherApiConfig.getApiKey() + "&q=" + "&aqi=no";
                WeatherResponse weatherResponse = restTemplate.getForObject(apiUrl, WeatherResponse.class);
                assert weatherResponse != null;
                return new Weather(weatherResponse);
            });
        } catch (HttpClientErrorException e) {
            ObjectMapper objectMapper = new ObjectMapper();
            ApiErrorResponse apiErrorResponse = objectMapper.readValue(e.getResponseBodyAsString(), ApiErrorResponse.class);
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST){
                throw new WeatherBadRequestException(apiErrorResponse.getError().getErrormessage());
            }
            else if (e.getStatusCode() == HttpStatus.FORBIDDEN){
                throw new WeatherForbiddenException(apiErrorResponse.getError().getErrormessage());
            }
            else if (e.getStatusCode() == HttpStatus.REQUEST_TIMEOUT)
                throw new WeatherRequestTimeoutException(apiErrorResponse.getError().getErrormessage());
        } catch (HttpServerErrorException e) {
            ObjectMapper objectMapper = new ObjectMapper();
            ApiErrorResponse apiErrorResponse = objectMapper.readValue(e.getResponseBodyAsString(), ApiErrorResponse.class);
            throw new InternalServerErrorException(e.getResponseBodyAsString());
        }
        return null;
    }
}
