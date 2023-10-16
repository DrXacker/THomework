package com.example.demo.model;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class Weather {
    private Long id;
    private String city;
    private double temperature;
    private LocalDateTime dateTime;

    public Weather(WeatherResponse weatherResponse) {
        this.city = weatherResponse.getLocation().getName();
        this.temperature = weatherResponse.getCurrent().getTemp_c();
        this.dateTime =weatherResponse.getCurrent().getLast_updated();
    }
}

