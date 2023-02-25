package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MapStorage;
import ru.javawebinar.topjava.storage.Storage;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MapUtil {
    private static final MapUtil INSTANCE = new MapUtil();
    private final Storage<Meal> storage;

    public MapUtil() {
        this.storage = new MapStorage();
        init();
    }

    public static MapUtil get() {
        return INSTANCE;
    }

    public void init() {
        List<Meal> meals = defaultMealList();
        for (Meal meal : meals) {
            storage.add(meal);
        }
    }

    public Storage<Meal> getStorage() {
        return storage;
    }

    public static List<Meal> defaultMealList() {
        return Arrays.asList(
                new Meal(new Random().nextInt(), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(new Random().nextInt(), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(new Random().nextInt(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(new Random().nextInt(), LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(new Random().nextInt(), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(new Random().nextInt(), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(new Random().nextInt(), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );
    }
}
