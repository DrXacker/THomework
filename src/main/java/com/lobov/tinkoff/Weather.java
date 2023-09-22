package com.lobov.tinkoff;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@ToString
class Weather {
    private int regionId;
    private String regionName;
    private double temperature;
    private Date dateTime;
}