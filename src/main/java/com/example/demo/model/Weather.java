package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class Weather {
    private Long id;
    private String city;
    private double temperature;
    private LocalDateTime dateTime;
}
