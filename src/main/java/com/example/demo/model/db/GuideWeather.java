package com.example.demo.model.db;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "GUIDE_WEATHER")
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GuideWeather {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String description;
}
