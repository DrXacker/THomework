package com.example.demo.model.db;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "WEATHER_IN_CITY")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WeatherInCity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "city_id")
    private UUID cityId;

    @Column(name = "guide_id")
    private UUID guideId;

    private LocalDateTime date;

    @Column(name = "temperature")
    private double temperature;
}
