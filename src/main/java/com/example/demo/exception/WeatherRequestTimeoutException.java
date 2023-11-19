package com.example.demo.exception;

public class WeatherRequestTimeoutException extends RuntimeException{
    public WeatherRequestTimeoutException(String msg) {
        super(msg);
    }
}
