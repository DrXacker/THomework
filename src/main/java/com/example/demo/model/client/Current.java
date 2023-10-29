package com.example.demo.model.client;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Current {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime last_updated;
    private double temp_c;
    private Condition condition;
}
