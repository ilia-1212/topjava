package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(15, 0), 2000);
        mealsTo.forEach(System.out::println);

       System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(15, 0), 2000));
    }



    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        boolean excess = false;
        List<UserMealWithExcess> resList = new ArrayList<>();
        int sumCalories = 0;

        Map<LocalDate, Boolean> excessMap = new LinkedHashMap<>();
        for (UserMeal meal : meals) {
            excessMap.put(meal.getDateTime().toLocalDate(), false);
        }
        for (Map.Entry<LocalDate, Boolean> dateEntry: excessMap.entrySet()) {
            excess = false;
            sumCalories = 0;
            for (UserMeal meal: meals) {
                if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime) &&
                        (dateEntry.getKey().equals(meal.getDateTime().toLocalDate()) )) {
                    sumCalories += meal.getCalories();
                    if (sumCalories >= caloriesPerDay) {
                        excess = true;
                    }
                }
            }
            excessMap.put(dateEntry.getKey(), excess);
        }

        for (UserMeal meal: meals) {
            if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                resList.add( new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), excessMap.getOrDefault(meal.getDateTime().toLocalDate(), false)));
            }
        }
        return resList;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> resMap = meals.
                stream().
                collect(Collectors.toMap(e->e.getDateTime().toLocalDate(), UserMeal::getCalories,Integer::sum, LinkedHashMap::new)
                );

        List<UserMeal> filterMeals = meals.
                stream().
                filter(s -> TimeUtil.isBetweenHalfOpen(s.getDateTime().toLocalTime(), startTime, endTime)).
                collect(Collectors.toList());

        List<UserMealWithExcess> resList = new LinkedList<>();
        for (UserMeal meal: filterMeals) {
            resList.add(new UserMealWithExcess(meal.getDateTime(),
                    meal.getDescription(),
                    meal.getCalories(),
                    resMap.get(meal.getDateTime().toLocalDate()).intValue() >= caloriesPerDay
                    ));

        }
        return resList;
    }
}

