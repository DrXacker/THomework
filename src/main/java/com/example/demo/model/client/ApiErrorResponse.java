package com.example.demo.model.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApiErrorResponse {
    @JsonProperty("error")
    private ErrorResponse error;

    // Геттеры и сеттеры
}

