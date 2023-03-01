package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeUtil {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(
            DateTimeFormatter.ISO_LOCAL_DATE + " " + DateTimeFormatter.ISO_LOCAL_TIME);

    public static boolean isBetweenHalfOpen(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) < 0;
    }

    public static String toHtml(LocalDateTime ldt) {
        return ldt.format(DATE_TIME_FORMATTER);
    }

    public static LocalDateTime fromHtml(String ldt) {
        return LocalDateTime.parse(ldt);
    }
}
