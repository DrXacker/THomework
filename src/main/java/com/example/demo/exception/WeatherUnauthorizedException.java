package com.example.demo.exception;

public class WeatherUnauthorizedException extends RuntimeException{
    public WeatherUnauthorizedException(String msg) {
        super(msg);
    }
}
