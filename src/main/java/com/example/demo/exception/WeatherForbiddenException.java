package com.example.demo.exception;

public class WeatherForbiddenException extends RuntimeException{
    public WeatherForbiddenException(String msg) { super(msg); }
}
