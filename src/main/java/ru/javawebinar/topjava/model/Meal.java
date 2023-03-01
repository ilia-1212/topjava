package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Meal {
    private int id;

    private LocalDateTime dateTime;

    private String description;

    private int calories;

    public Meal() {
    }

    public Meal(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public Meal(LocalDateTime ldt, String description, int calories) {
        this.dateTime = ldt;
        this.description = description;
        this.calories = calories;
    }

    public Meal(int id, LocalDateTime ldt, String description, int calories) {
        this.id = id;
        this.dateTime = ldt;
        this.description = description;
        this.calories = calories;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }
}
