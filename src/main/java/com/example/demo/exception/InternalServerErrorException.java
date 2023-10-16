package com.example.demo.exception;

public class InternalServerErrorException extends RuntimeException{
    public InternalServerErrorException(String msg) {
        super(msg);
    }
}