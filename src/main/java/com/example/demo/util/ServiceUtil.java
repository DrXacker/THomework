package com.example.demo.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ServiceUtil {
    private ServiceUtil() {}

    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final DateTimeFormatter CUSTOM_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

    public static boolean isSameDay(LocalDate date1, LocalDate date2) {
        return date1.format(CUSTOM_FORMATTER).equals(date2.format(CUSTOM_FORMATTER));
    }
}
