package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class Meal {
    private Integer uuid;

    private LocalDateTime dateTime;

    private String description;

    private int calories;

    public Meal() {
    }

    public Meal(Integer uuid, String description) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(description, "fullName must not be null");
        this.uuid = uuid;
        this.description = description;
    }

    public Meal(Integer uuid, LocalDateTime ldt, String description, Integer calories) {
        this(uuid, description);
        this.dateTime = ldt;
        this.calories = calories;
    }

    public void setUuid(Integer uuid) {
        this.uuid = uuid;
    }

    public Integer getUuid() {
        return uuid;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meal meal = (Meal) o;
        return calories == meal.calories && uuid.equals(meal.uuid) && Objects.equals(dateTime, meal.dateTime) && Objects.equals(description, meal.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, dateTime, description, calories);
    }
}
