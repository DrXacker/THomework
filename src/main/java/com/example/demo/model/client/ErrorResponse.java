package com.example.demo.model.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

@Setter
@Getter
@AllArgsConstructor
public class ErrorResponse {
    @JsonProperty("code")
    private int errorCode;

    @JsonProperty("message")
    private String errormessage;

}
