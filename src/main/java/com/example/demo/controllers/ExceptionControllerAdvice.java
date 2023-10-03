package com.example.demo.controllers;

import com.example.demo.exception.WeatherBadRequestException;
import com.example.demo.exception.WeatherNotFoundException;
import com.example.demo.payload.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(WeatherBadRequestException.class)
    public ResponseEntity<ErrorResponse> handleCustomDataBadRequestExceptions(Exception e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(new ErrorResponse(status, e.getMessage()), status);
    }

    @ExceptionHandler(WeatherNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCustomDataNotFoundExceptions(Exception e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(new ErrorResponse(status, e.getMessage()), status);
    }
}
