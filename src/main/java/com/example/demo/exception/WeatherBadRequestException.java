package com.example.demo.exception;

public class WeatherBadRequestException extends RuntimeException {
    public WeatherBadRequestException(String msg) {
        super(msg);
    }
}