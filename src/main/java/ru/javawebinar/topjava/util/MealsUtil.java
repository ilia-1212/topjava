package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static ru.javawebinar.topjava.util.TimeUtil.isBetweenHalfOpen;

public class MealsUtil {
    public static final int MAX_CALORIES = 2000;
    private static final AtomicInteger atomicInteger = new AtomicInteger(0);

    public static int next_seq() {
        return atomicInteger.incrementAndGet();
    }

    public static List<MealTo> filteredByCycles(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> sumCalByDay = new HashMap<>();
        meals.forEach(m -> sumCalByDay.merge(m.getDate(), m.getCalories(), Integer::sum));

        List<MealTo> mealTo = new ArrayList<>();
        meals.forEach(m -> {
            if (isBetweenHalfOpen(m.getDateTime().toLocalTime(), startTime, endTime)) {
                mealTo.add(
                        new MealTo(next_seq(),
                                m.getDateTime(),
                                m.getDescription(),
                                m.getCalories(), sumCalByDay.get(m.getDate()) > caloriesPerDay)
                );
            }
        });
        return mealTo;
    }

    public static List<MealTo> filteredByStreams(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = meals
                .stream()
                .collect(Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
                        //Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
                );
        return meals
                .stream()
                .filter(meal -> isBetweenHalfOpen(meal.getTime(), startTime, endTime))
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(toList());
    }

    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }

    public static boolean isEmpty(String str) {
        if (str == null || str.trim().length() == 0) {
            return true;
        }
        return false;
    }
}
