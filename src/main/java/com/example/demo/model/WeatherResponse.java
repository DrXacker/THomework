package com.example.demo.model;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Setter
@Getter
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {
    private Location location;
    private Current current;
}





