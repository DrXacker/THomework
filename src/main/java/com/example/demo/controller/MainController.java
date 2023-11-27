package com.example.demo.controller;

import com.example.demo.model.Weather;
import com.example.demo.model.db.User;
import com.example.demo.service.client.WeatherApiClient;
import com.example.demo.service.hibernate.UserService;
import com.example.demo.transaction.jpa.JpaTransaction;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MainController {
    @Autowired
    private JpaTransaction jpaTransaction;

    @Autowired
    private WeatherApiClient weatherApiClient;

    @Autowired
    private UserService userService;

    @GetMapping("/api/weather")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<Weather> getAll() {
        return jpaTransaction.getAllWeatherData();
    }

    @PostMapping("/api/admin/{city}")
    @PreAuthorize("hasRole('ADMIN')")
    public void addCity(@PathVariable String city){
        jpaTransaction.insertData(weatherApiClient.getCurrentWeather(city));
    }

    @PostMapping(value = "/registration", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PermitAll
    public void registration(@RequestBody User user) {
        userService.saveUser(user);
    }
}
