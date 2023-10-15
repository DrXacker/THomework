package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WeatherInCity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Long city_id;
    private Long guide_id;
    private Date date;
    private double temperature;
}
