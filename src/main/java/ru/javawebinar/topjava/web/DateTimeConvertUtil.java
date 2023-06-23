package ru.javawebinar.topjava.web;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeConvertUtil {

    public static class StringToLocalDateConverter implements Converter<String, LocalDate> {
        private String datePattern = "yyyy-MM-dd";

        public String getDatePattern() {
            return datePattern;
        }

        public void setDatePattern(String datePattern) {
            this.datePattern = datePattern;
        }

        @Override
        public LocalDate convert(String source) {
            return LocalDate.parse(source, DateTimeFormatter.ofPattern(datePattern));
        }
    }

    public static class StringToLocalTimeConverter implements Converter<String, LocalTime> {
        private String timePattern = "HH:mm";

        public String gettimePattern() {
            return timePattern;
        }

        public void settimePattern(String timePattern) {
            this.timePattern = timePattern;
        }

        @Override
        public LocalTime convert(String source) {
            return LocalTime.parse(source, DateTimeFormatter.ofPattern(timePattern));
        }
    }
}
