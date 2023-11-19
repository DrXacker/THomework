package com.example.demo.exception;

public class WeatherNotFoundException extends RuntimeException {
    public WeatherNotFoundException(String msg) {
        super(msg);
    }
}