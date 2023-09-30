package com.example.demo.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ServiceUtil {
    private ServiceUtil() {}

    public static boolean isSameDay(LocalDateTime date1, LocalDateTime date2) {
        DateTimeFormatter CUSTOM_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date1.format(CUSTOM_FORMATTER).equals(date2.format(CUSTOM_FORMATTER));
    }
}
