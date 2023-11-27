package com.example.demo.kafka;

import com.example.demo.model.Weather;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class WeatherProducer {

    private final KafkaTemplate<String, Weather> kafkaTemplate;

    public WeatherProducer(KafkaTemplate<String, Weather> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void produceWeatherData(Weather weather) {
        kafkaTemplate.send("weather-topic", weather.getCity(), weather);
    }
}

