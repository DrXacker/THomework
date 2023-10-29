package com.example.demo.model.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.http.HttpStatusCode;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ApiErrorResponse{
    private HttpStatusCode errorStatusCode;
}

