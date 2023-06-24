package ru.javawebinar.topjava.web;

import org.springframework.core.convert.converter.Converter;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDate;
import java.time.LocalTime;

public class DateTimeConvertUtil {

    public static class StringToLocalDateConverter implements Converter<String, LocalDate> {

        @Override
        public LocalDate convert(String source) {
            // LocalDate.parse(source);
            // escape error java.time.format.DateTimeParseException: Text '' could not be parsed at index 0
            return DateTimeUtil.parseLocalDate(source);

        }
    }

    public static class StringToLocalTimeConverter implements Converter<String, LocalTime> {

        @Override
        public LocalTime convert(String source) {
            //LocalTime.parse(source);
            // escape error java.time.format.DateTimeParseException: Text '' could not be parsed at index 0
            return DateTimeUtil.parseLocalTime(source);
        }
    }
}
