package com.lobov.tinkoff;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Weather> weatherList = new ArrayList<>();
        weatherList.add(new Weather(1, "Region1", 25.5, new Date()));
        weatherList.add(new Weather(2, "Region2", 28.0, new Date()));
        weatherList.add(new Weather(3, "Region3", 22.8, new Date()));
        weatherList.add(new Weather(1, "Region1", 29.0, new Date()));
        weatherList.add(new Weather(4, "Region4", 28.0, new Date()));
        weatherList.add(new Weather(4, "Region4", 22.0, new Date()));

        Map<String, Double> averageTemperatureByRegion = calculateAverageTemperature(weatherList);
        System.out.println("Average temperature in each region:");
        averageTemperatureByRegion.forEach((region, avgTemperature) ->
                System.out.println("   " + region + ": " + avgTemperature));

        double temperatureThreshold = 25.0;
        List<String> regionsAboveThreshold = findRegionsWithTemperatureGreaterThan(weatherList, temperatureThreshold);
        System.out.println("Regions with temperatures above " + temperatureThreshold + ": " + regionsAboveThreshold);

        Map<Integer, List<Double>> mapByRegionId = convertToMapByRegionId(weatherList);
        System.out.println("Map by unique region identifier: " + mapByRegionId);

        Map<Double, List<Weather>> mapByTemperature = convertToMapByTemperature(weatherList);
        System.out.println("Map by temperature: " + mapByTemperature);
    }


    public static Map<String, Double> calculateAverageTemperature(List<Weather> weatherList) {
        return weatherList.stream()
                .collect(Collectors.groupingBy(Weather::getRegionName,
                            Collectors.averagingDouble(Weather::getTemperature)));
    }

    public static List<String> findRegionsWithTemperatureGreaterThan(List<Weather> weatherList, double temperatureThreshold) {
        return weatherList.stream()
                .filter(weather -> weather.getTemperature() > temperatureThreshold)
                .map(Weather::getRegionName)
                .distinct()
                .toList();
    }

    public static Map<Integer, List<Double>> convertToMapByRegionId(List<Weather> weatherList) {
        return weatherList.stream()
                .collect(Collectors.groupingBy(Weather::getRegionId,
                        Collectors.mapping(Weather::getTemperature, Collectors.toList())));
    }

    public static Map<Double, List<Weather>> convertToMapByTemperature(List<Weather> weatherList) {
        return weatherList.stream()
                .collect(Collectors.groupingBy(
                        Weather::getTemperature,
                        Collectors.toList()
                ));
    }
}
