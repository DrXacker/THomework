package com.example.demo.model;

import com.example.demo.model.client.WeatherResponse;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class Weather {
    private UUID id;
    private String city;
    private double temperature;
    private LocalDateTime dateTime;
    private String description;

    public Weather(WeatherResponse weatherResponse) {
        this.city = weatherResponse.getLocation().getName();
        this.temperature = weatherResponse.getCurrent().getTemp_c();
        this.dateTime = weatherResponse.getCurrent().getLast_updated();
        this.description = weatherResponse.getCurrent().getCondition().getText();
    }
}
