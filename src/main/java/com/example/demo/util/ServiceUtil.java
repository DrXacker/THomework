package com.example.demo.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ServiceUtil {
    private ServiceUtil() {}

    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm";
    private static final DateTimeFormatter CUSTOM_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    public static boolean isSameDay(LocalDateTime date1, LocalDateTime date2) {
        return date1.format(CUSTOM_FORMATTER).equals(date2.format(CUSTOM_FORMATTER));
    }
}
