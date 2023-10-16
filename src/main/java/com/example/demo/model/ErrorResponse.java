package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorResponse {
    @JsonProperty("code")
    private int errorCode;

    @JsonProperty("message")
    private String errormessage;
}
